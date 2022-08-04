package dev.mayuna.nostalgiamanager.modules.enhancedchat.listeners;

import dev.mayuna.nostalgiamanager.modules.enhancedchat.EnhancedChatModule;
import dev.mayuna.nostalgiamanager.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class EnhancedChatListener implements Listener {

    public static final Pattern PING_PATTERN = Pattern.compile("@[a-zA-Z_]*");

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (!EnhancedChatModule.getInstance().isEnabled()) {
            return;
        }

        String message = event.getMessage();
        List<String> replacedNicknames = new LinkedList<>();

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!message.toLowerCase().contains("@" + onlinePlayer.getName().toLowerCase())) {
                continue;
            }

            String nickname = onlinePlayer.getName();

            if (!replacedNicknames.contains(nickname)) {
                replacedNicknames.add(nickname);
                message = message.replace("@" + nickname, "§e@" + nickname + "§r");

                if (!EnhancedChatModule.hasPlayerDisabledPings(onlinePlayer.getUniqueId())) {
                    onlinePlayer.playSound(onlinePlayer.getLocation(), Config.Modules.EnhancedChat.getPingSound(), 0.7f, 1.0f);
                }
            }
        }

        event.setMessage(message);
    }

    @EventHandler
    public void onTabComplete(PlayerChatTabCompleteEvent event) {
        String lastToken = event.getLastToken();

        if (lastToken.contains("@")) {
            String possibleNickname = lastToken.replace("@", "");

            List<String> playerNicknames = new LinkedList<>();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.getName().startsWith(possibleNickname)) {
                    playerNicknames.add("@" + onlinePlayer.getName());
                }
            }

            event.getTabCompletions().clear();
            event.getTabCompletions().addAll(playerNicknames);
        }
    }
}
