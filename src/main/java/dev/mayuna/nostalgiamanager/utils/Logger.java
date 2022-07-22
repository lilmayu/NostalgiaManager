package dev.mayuna.nostalgiamanager.utils;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

public class Logger {

    public static final String PREFIX = "[NM]";

    private static final Map<String, String> classPrefixes = new HashMap<>();

    public static void addClassPrefix(Class<?> clazz, String prefix) {
        classPrefixes.put(clazz.getName(), prefix.replace("Module", ""));
    }

    public static void info(String message) {
        Bukkit.getLogger().info(PREFIX + getClassPrefix() + message);
    }

    public static void warn(String message) {
        Bukkit.getLogger().warning(PREFIX + getClassPrefix() + message);
    }

    public static void error(String message) {
        Bukkit.getLogger().severe(PREFIX + getClassPrefix() + message);
    }

    public static void debug(String message) {
        if (Config.isDebug()) {
            Bukkit.getLogger().info("[DEBUG] " + PREFIX + getClassPrefix() + message);
        }
    }

    private static String getClassPrefix() {
        String genesisCaller = Thread.currentThread().getStackTrace()[3].getClassName();

        if (classPrefixes.containsKey(genesisCaller)) {
            return " [" + classPrefixes.get(genesisCaller) + "] ";
        }

        return " ";
    }
}
