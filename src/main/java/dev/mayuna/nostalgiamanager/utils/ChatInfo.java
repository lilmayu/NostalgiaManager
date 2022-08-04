package dev.mayuna.nostalgiamanager.utils;

import org.bukkit.command.CommandSender;

public class ChatInfo {

    public static void info(CommandSender sender, String message) {
        sender.sendMessage("§8[#] §7" + processColor(message, "§7"));
    }

    public static void warning(CommandSender sender, String message) {
        sender.sendMessage("§6[!] §6" + processColor(message, "§6"));
    }

    public static void error(CommandSender sender, String message) {
        sender.sendMessage("§c§l[!!] §c" + processColor(message, "§c"));
    }

    public static void success(CommandSender sender, String message) {
        sender.sendMessage("§a§l[>] §a" + processColor(message, "§a"));
    }

    private static String processColor(String message, String color) {
        return message.replace("{c}", color);
    }
}
