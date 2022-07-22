package dev.mayuna.nostalgiamanager.commands;

import dev.mayuna.nostalgiamanager.utils.ChatInfo;
import dev.mayuna.nostalgiamanager.utils.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class NostalgiaReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        ChatInfo.info(commandSender, "Reloading...");
        Config.load();
        ChatInfo.success(commandSender, "Config reloaded.");
        return true;
    }
}
