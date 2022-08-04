package dev.mayuna.nostalgiamanager.listeners;

import dev.mayuna.nostalgiamanager.Main;
import dev.mayuna.nostalgiamanager.utils.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        new Thread(() -> {
            Player player = event.getPlayer();
            UUID playerUUID = player.getUniqueId();
            Logger.info("Asynchronously loading player data " + playerUUID + "...");

            Main.getPumpk1n().getOrCreateDataHolder(playerUUID).save();
        }).start();
    }
}
