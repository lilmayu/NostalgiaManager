package dev.mayuna.nostalgiamanager.modules.enhancedchat.commands;

import dev.mayuna.nostalgiamanager.modules.enhancedchat.EnhancedChatModule;
import dev.mayuna.nostalgiamanager.modules.enhancedchat.data.EnhancedChatSettingsData;
import dev.mayuna.nostalgiamanager.utils.ChatInfo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatPingsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!EnhancedChatModule.getInstance().isEnabled()) {
            ChatInfo.error(commandSender, "This command is disabled.");
            return true;
        }

        if ((!(commandSender instanceof Player))) {
            ChatInfo.error(commandSender, "This command is for players!");
            return true;
        }

        Player player = (Player) commandSender;

        if (args.length == 0) {
            boolean disabledChatPings = EnhancedChatModule.hasPlayerDisabledPings(player.getUniqueId());

            ChatInfo.info(player, "You have currently " + (disabledChatPings ? "§cdisabled{c}" : "§aenabled{c}") + " chat pings.");
            return true;
        }

        EnhancedChatSettingsData settingsData = EnhancedChatModule.getSettingsForPlayer(player.getUniqueId());

        switch (args[0]) {
            case "on": {
                settingsData.setDisabledPings(false);
                ChatInfo.success(player, "Successfully turned §eon{c} chat pings!");

                break;
            }
            case "off": {
                settingsData.setDisabledPings(true);
                ChatInfo.success(player, "Successfully turned §coff{c} chat pings!");

                break;
            }
            default: {
                ChatInfo.error(player, "Incorrect usage! Syntax: /chat-pings <on|off>");

                break;
            }
        }

        settingsData.getDataHolderParent().save();

        return true;
    }
}
