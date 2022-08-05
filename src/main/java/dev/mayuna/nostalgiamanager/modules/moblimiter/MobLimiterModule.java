package dev.mayuna.nostalgiamanager.modules.moblimiter;

import dev.mayuna.nostalgiamanager.Main;
import dev.mayuna.nostalgiamanager.api.Module;
import dev.mayuna.nostalgiamanager.utils.ChatInfo;
import dev.mayuna.nostalgiamanager.utils.Config;
import dev.mayuna.nostalgiamanager.utils.Logger;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MobLimiterModule implements Module {

    private static @Getter MobLimiterModule instance;
    private @Getter BukkitTask mobCheckTask;

    @Override
    public boolean isEnabled() {
        return Config.Modules.MobLimiter.isEnabled();
    }

    @Override
    public void onLoad() {
        instance = this;
        Main pluginInstance = Main.getInstance();

        Logger.info("Loading...");

        Logger.info("Creating scheduler...");
        mobCheckTask = Bukkit.getScheduler().runTaskTimerAsynchronously(pluginInstance, this::checkMobs, 0, Config.Modules.MobLimiter.getCheckEveryTicks());
    }

    private void checkMobs() {
        if (!isEnabled()) {
            return;
        }

        int entityLimit = Config.Modules.MobLimiter.getEntityLimit();
        Logger.info("Checking for chunks with mob count over " + entityLimit + "...");
        AtomicInteger totalRemovedMobs = new AtomicInteger();
        Map<Chunk, Integer> removedMobsPerChunk = new HashMap<>();

        Bukkit.getWorlds().forEach(world -> {
            if (Config.Modules.MobLimiter.getIgnoredWorlds().contains(world.getName())) {
                return;
            }

            for (Chunk chunk : world.getLoadedChunks()) {
                int mobCount = 0;

                for (Entity entity : chunk.getEntities()) {
                    if (!(entity instanceof Creature)) { // EnderDragon is not a Creature
                        continue;
                    }

                    if (entity instanceof Wither) {
                        continue;
                    }

                    if (entity.isDead()) {
                        continue;
                    }

                    if (mobCount <= entityLimit) {
                        mobCount++;
                        continue;
                    }

                    Creature creature = (Creature) entity;
                    creature.remove();

                    totalRemovedMobs.getAndIncrement();
                    Integer currentCount = removedMobsPerChunk.get(chunk);

                    if (currentCount != null) {
                        removedMobsPerChunk.put(chunk, ++currentCount);
                    } else {
                        removedMobsPerChunk.put(chunk, 1);
                    }
                }
            }
        });

        Logger.info("Checking done. Removed totally " + totalRemovedMobs.get() + " mobs from " + removedMobsPerChunk.size() + " chunk(s)!");

        removedMobsPerChunk.keySet().forEach(chunk -> {
            int mobCount = removedMobsPerChunk.get(chunk);

            for (Entity entity : chunk.getEntities()) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;

                    ChatInfo.info(player, "Removed " + mobCount + " " + (mobCount == 1 ? "entity" : "entities") + " in your current chunk due to a large number of entities there.");
                 }
            }

            Logger.info("Mobs removed from chunk " + chunk.getX() + " " + chunk.getZ() + ": " + mobCount);
        });
    }
}
