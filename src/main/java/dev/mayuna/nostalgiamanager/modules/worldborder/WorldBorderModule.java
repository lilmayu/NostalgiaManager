package dev.mayuna.nostalgiamanager.modules.worldborder;

import dev.mayuna.nostalgiamanager.Main;
import dev.mayuna.nostalgiamanager.api.Module;
import dev.mayuna.nostalgiamanager.commands.NostalgiaReloadCommand;
import dev.mayuna.nostalgiamanager.modules.worldborder.listeners.PlayerMovementWorldBorderListener;
import dev.mayuna.nostalgiamanager.utils.Config;
import dev.mayuna.nostalgiamanager.utils.Logger;
import org.bukkit.plugin.PluginManager;

public class WorldBorderModule implements Module {

    @Override
    public void onLoad() {
        Main instance = Main.getInstance();

        Logger.info("Loading...");

        Logger.info("Registering commands...");
        instance.getCommand("nostalgia-reload").setExecutor(new NostalgiaReloadCommand());

        Logger.info("Registering listeners...");
        PluginManager pm = instance.getServer().getPluginManager();
        pm.registerEvents(new PlayerMovementWorldBorderListener(), instance);
    }

    @Override
    public boolean isEnabled() {
        return Config.Modules.WorldBorder.isEnabled();
    }
}
