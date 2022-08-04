package dev.mayuna.nostalgiamanager;

import dev.mayuna.nostalgiamanager.commands.NostalgiaReloadCommand;
import dev.mayuna.nostalgiamanager.listeners.PlayerJoinListener;
import dev.mayuna.nostalgiamanager.utils.Config;
import dev.mayuna.nostalgiamanager.utils.Logger;
import dev.mayuna.pumpk1n.Pumpk1n;
import dev.mayuna.pumpk1n.impl.FolderStorageHandler;
import dev.mayuna.pumpk1n.impl.SQLiteStorageHandler;
import lombok.Getter;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static @Getter Main instance;

    private static @Getter Pumpk1n pumpk1n;

    private static @Getter long start;

    public static long getElapsedTime() {
        return System.currentTimeMillis() - start;
    }

    @Override
    public void onEnable() {
        instance = this;
        start = System.currentTimeMillis();
        PluginDescriptionFile pluginDescription = this.getDescription();

        Logger.info("");
        Logger.info("# NostalgiaManager @ " + pluginDescription.getVersion());
        Logger.info("> Made by: " + pluginDescription.getAuthors());
        Logger.info("");
        Logger.info("Loading... This should be quick.");

        // TODO: Check jestli je nová verze na githubu

        Logger.info("Loading config...");
        Config.load();

        Logger.info("Loading Pumpk1n...");
        loadPumpk1n();

        Logger.info("Registering listeners...");
        registerListeners();

        Logger.info("Registering commands...");
        loadCommands();

        Logger.info("Preparing modules...");
        Modules.prepareModules();

        Logger.info("Loading modules...");
        Modules.loadModules();

        Logger.info("");
        Logger.info("Loading done in " + (System.currentTimeMillis() - start) + "ms!");
        Logger.info("");
    }

    @Override
    public void onDisable() {
        Logger.info("Disabling...");

        Logger.info("Unloading modules...");
        Modules.unloadModules();

        Logger.info("Saving Pumpk1n...");
        pumpk1n.getDataHolderList().forEach(dataHolder -> pumpk1n.saveDataHolder(dataHolder));

        Logger.info("o/");
        instance = null;
    }

    private void registerListeners() {
        PluginManager pm = Main.getInstance().getServer().getPluginManager();

        pm.registerEvents(new PlayerJoinListener(), this);
    }

    private void loadCommands() {
        instance.getCommand("nostalgia-reload").setExecutor(new NostalgiaReloadCommand());
    }

    private void loadPumpk1n() {
        pumpk1n = new Pumpk1n(new FolderStorageHandler(Config.Pumpk1n.getDataFolder()));
        Logger.info("Preparing Pumpk1n...");
        pumpk1n.prepareStorage();
    }
}
