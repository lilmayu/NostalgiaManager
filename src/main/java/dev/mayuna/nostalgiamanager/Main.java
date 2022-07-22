package dev.mayuna.nostalgiamanager;

import dev.mayuna.nostalgiamanager.modules.worldborder.WorldBorderModule;
import dev.mayuna.nostalgiamanager.utils.Config;
import dev.mayuna.nostalgiamanager.utils.Logger;
import lombok.Getter;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static @Getter Main instance;

    private static @Getter WorldBorderModule worldBorderModule;

    @Override
    public void onEnable() {
        instance = this;
        PluginDescriptionFile pluginDescription = this.getDescription();

        Logger.info("");
        Logger.info("# NostalgiaManager @ " + pluginDescription.getVersion());
        Logger.info("> Made by: " + pluginDescription.getAuthors());
        Logger.info("");
        Logger.info("Loading... This should be quick.");

        // TODO: Check jestli je nová verze na githubu

        long start = System.currentTimeMillis();

        Logger.info("Loading config...");
        Config.load();

        Logger.info("Preparing modules...");
        Modules.prepareModules();

        Logger.info("Loading enabled modules...");
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

        Logger.info("o/");
        instance = null;
    }
}
