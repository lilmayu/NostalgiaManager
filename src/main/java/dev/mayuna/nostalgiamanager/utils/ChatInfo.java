package dev.mayuna.nostalgiamanager.utils;

import org.bukkit.command.CommandSender;

public class ChatInfo {

    public static void info(CommandSender sender, String message) {
        sender.sendMessage("§8[#] §7" + message);
    }

    public static void warning(CommandSender sender, String message) {
        sender.sendMessage("§6[!] §6" + message);
    }

    public static void error(CommandSender sender, String message) {
        sender.sendMessage("§c§l[!!] §c" + message);
    }

    public static void success(CommandSender sender, String message) {
        sender.sendMessage("§a§l[>] §a" + message);
    }
}
