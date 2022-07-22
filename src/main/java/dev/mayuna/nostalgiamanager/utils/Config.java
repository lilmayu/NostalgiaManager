package dev.mayuna.nostalgiamanager.utils;

import dev.mayuna.nostalgiamanager.Main;
import lombok.Getter;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

public class Config {

    private static @Getter boolean debug;

    public static void load() {
        Main instance = Main.getInstance();

        instance.getConfig().options().copyDefaults(true);
        instance.saveDefaultConfig();
        instance.reloadConfig();

        Configuration config = instance.getConfig();

        debug = config.getBoolean("debug");

        Modules.WorldBorder.load(config.getConfigurationSection("modules.world-border"));
    }

    public static class Modules {

        public static class WorldBorder {

            private static @Getter boolean enabled;
            private static @Getter boolean ignoreCreative;
            private static @Getter boolean ignoreOPs;

            public static void load(ConfigurationSection section) {
                enabled = section.getBoolean("enabled");
                ignoreCreative = section.getBoolean("ignore-creative");
                ignoreOPs = section.getBoolean("ignore-ops");

                Distances.overworld = section.getLong("distances.overworld");
                Distances.nether = section.getLong("distances.nether");
                Distances.end = section.getLong("distances.end");
            }

            public static class Distances {

                private static @Getter long overworld, nether, end;
            }
        }
    }
}
