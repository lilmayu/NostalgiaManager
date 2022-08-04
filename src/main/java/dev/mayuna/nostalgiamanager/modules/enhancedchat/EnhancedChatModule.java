package dev.mayuna.nostalgiamanager.modules.enhancedchat;

import dev.mayuna.nostalgiamanager.Main;
import dev.mayuna.nostalgiamanager.api.Module;
import dev.mayuna.nostalgiamanager.modules.enhancedchat.commands.ChatPingsCommand;
import dev.mayuna.nostalgiamanager.modules.enhancedchat.data.EnhancedChatSettingsData;
import dev.mayuna.nostalgiamanager.modules.enhancedchat.listeners.EnhancedChatListener;
import dev.mayuna.nostalgiamanager.utils.Config;
import dev.mayuna.nostalgiamanager.utils.Logger;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;

import java.util.UUID;

public class EnhancedChatModule implements Module {

    private static @Getter EnhancedChatModule instance;

    @Override
    public boolean isEnabled() {
        return Config.Modules.EnhancedChat.isEnabled();
    }

    @Override
    public void onLoad() {
        instance = this;
        Main pluginInstance = Main.getInstance();
        Logger.info("Loading...");

        Logger.info("Registering listeners...");
        PluginManager pm = pluginInstance.getServer().getPluginManager();
        pm.registerEvents(new EnhancedChatListener(), pluginInstance);

        Logger.info("Registering commands...");
        pluginInstance.getCommand("chat-pings").setExecutor(new ChatPingsCommand());
    }

    public static boolean hasPlayerDisabledPings(UUID playerUUID) {
        return getSettingsForPlayer(playerUUID).isDisabledPings();
    }

    public static EnhancedChatSettingsData getSettingsForPlayer(UUID playerUUID) {
        return Main.getPumpk1n().getOrCreateDataHolder(playerUUID).getOrCreateDataElement(EnhancedChatSettingsData.class);
    }
}
