package dev.buha007.owlutils.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import dev.buha007.owlutils.Main;
import dev.buha007.owlutils.Var;

public class MentionNotifier implements Listener {

    public MentionNotifier() {
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        String notification = Var.MENTION_FORMAT;
        notification = notification.replace("{PLAYER}", e.getPlayer().getName());

        String message = e.getMessage().toLowerCase();
        for (String word : message.split(" ")) {

            Player player = Bukkit.getPlayerExact(word);
            if (player != null && player.hasPermission("owl.mention.recive")) {
                Main.msg(player, notification);
                player.playSound(player.getLocation(), Var.SOUND, 10f, 1f);
            }
        }
    }
}