package dev.mayuna.nostalgiamanager.modules.worldborder.listeners;

import dev.mayuna.nostalgiamanager.utils.ChatInfo;
import dev.mayuna.nostalgiamanager.utils.Config;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;

public class PlayerMovementWorldBorderListener implements Listener {

    @EventHandler
    public void onPlayerPortalEvent(PlayerPortalEvent event) {
        if (!Config.Modules.WorldBorder.isEnabled()) {
            return;
        }

        Player player = event.getPlayer();

        if (Config.Modules.WorldBorder.isIgnoreCreative() && player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        if (Config.Modules.WorldBorder.isIgnoreOPs() && player.isOp()) {
            return;
        }

        Location location = event.getTo();

        World.Environment environment = location.getWorld().getEnvironment();

        double locX = location.getX();
        double locZ = location.getZ();

        switch (environment) {
            case NORMAL:
                long maxDistancePositiveOverworld = Math.abs(Config.Modules.WorldBorder.Distances.getOverworld());

                if (isOverLimit(locX, maxDistancePositiveOverworld)) {
                    event.setCancelled(true);
                    break;
                }

                if (isOverLimit(locZ, maxDistancePositiveOverworld)) {
                    event.setCancelled(true);
                    break;
                }

                break;
            case NETHER:
                long maxDistancePositiveNether = Math.abs(Config.Modules.WorldBorder.Distances.getNether());

                if (isOverLimit(locX, maxDistancePositiveNether)) {
                    event.setCancelled(true);
                    break;
                }

                if (isOverLimit(locZ, maxDistancePositiveNether)) {
                    event.setCancelled(true);
                    break;
                }

                break;
        }

        if (event.isCancelled()) {
            sendMessagePortal(player);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        if (!Config.Modules.WorldBorder.isEnabled()) {
            return;
        }

        Player player = event.getPlayer();

        if (Config.Modules.WorldBorder.isIgnoreCreative() && player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        if (Config.Modules.WorldBorder.isIgnoreOPs() && player.isOp()) {
            return;
        }

        Location location = event.getTo();

        if (location.getBlockX() == event.getFrom().getBlockX() && location.getBlockY() == event.getFrom().getBlockY() && location.getBlockZ() == event.getFrom().getBlockZ()) {
            return;
        }

        World.Environment environment = location.getWorld().getEnvironment();

        double locX = location.getX();
        double locZ = location.getZ();

        switch (environment) {
            case NORMAL:
                long maxDistancePositiveOverworld = Math.abs(Config.Modules.WorldBorder.Distances.getOverworld());

                if (isOverLimit(locX, maxDistancePositiveOverworld)) {
                    event.setCancelled(true);
                    location.setX(getTeleportationPosition(locX, maxDistancePositiveOverworld));
                    player.teleport(location);
                    break;
                }

                if (isOverLimit(locZ, maxDistancePositiveOverworld)) {
                    event.setCancelled(true);
                    location.setZ(getTeleportationPosition(locZ, maxDistancePositiveOverworld));
                    player.teleport(location);
                    break;
                }

                break;
            case NETHER:
                long maxDistancePositiveNether = Math.abs(Config.Modules.WorldBorder.Distances.getNether());

                if (isOverLimit(locX, maxDistancePositiveNether)) {
                    event.setCancelled(true);
                    location.setX(getTeleportationPosition(locX, maxDistancePositiveNether));
                    player.teleport(location);
                    break;
                }

                if (isOverLimit(locZ, maxDistancePositiveNether)) {
                    event.setCancelled(true);
                    location.setZ(getTeleportationPosition(locZ, maxDistancePositiveNether));
                    player.teleport(location);
                    break;
                }

                break;
            case THE_END:
                long maxDistancePositiveEnd = Math.abs(Config.Modules.WorldBorder.Distances.getEnd());

                if (isOverLimit(locX, maxDistancePositiveEnd)) {
                    event.setCancelled(true);
                    location.setX(getTeleportationPosition(locX, maxDistancePositiveEnd));
                    player.teleport(location);
                    break;
                }

                if (isOverLimit(locZ, maxDistancePositiveEnd)) {
                    event.setCancelled(true);
                    location.setZ(getTeleportationPosition(locZ, maxDistancePositiveEnd));
                    player.teleport(location);
                    return;
                }
                break;
        }

        if (event.isCancelled()) {
            sendMessageMovement(player);
        }
    }

    private void sendMessageMovement(Player player) {
        ChatInfo.error(player, "You've reached world border, you cannot move further.");
    }

    private void sendMessagePortal(Player player) {
        ChatInfo.error(player, "Exit portal reaches outside world border, cannot teleport.");
    }

    private boolean isOverLimit(double location, long limitPositive) {
        limitPositive = Math.abs(limitPositive);
        long limitNegative = limitPositive * -1;

        if (location < 0) {
            return limitNegative > location;
        } else {
            return limitPositive < location;
        }
    }

    private double getTeleportationPosition(double location, long limitPositive) {
        limitPositive = Math.abs(limitPositive);
        long limitNegative = limitPositive * -1;

        if (location < 0) {
            return limitNegative + 0.5;
        } else {
            return limitPositive - 0.5;
        }
    }
}
