package dev.mayuna.nostalgiamanager.utils;

import dev.mayuna.nostalgiamanager.Main;
import lombok.Getter;
import org.bukkit.Sound;
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

        Pumpk1n.load(config.getConfigurationSection("pumpk1n"));
        Modules.WorldBorder.load(config.getConfigurationSection("modules.world-border"));
        Modules.EnhancedChat.load(config.getConfigurationSection("modules.enhanced-chat"));
    }

    public static class Pumpk1n {

        private static @Getter String dataFolder;

        public static void load(ConfigurationSection section) {
            dataFolder = section.getString("data-folder");
        }
    }

    public static class Modules {

        public static class EnhancedChat {

            private static @Getter boolean enabled;
            private static @Getter Sound pingSound = Sound.CHICKEN_EGG_POP;

            public static void load(ConfigurationSection section) {
                enabled = section.getBoolean("enabled");

                try {
                    pingSound = Sound.valueOf(section.getString("ping-sound"));
                } catch (IllegalArgumentException exception) {
                    exception.printStackTrace();
                    Logger.error("Unknown sound: " + section.getString("ping-sound") + "! Using default CHICKEN_EGG_POP sound...");
                }
            }
        }

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
