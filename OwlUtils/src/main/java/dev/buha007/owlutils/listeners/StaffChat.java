package dev.buha007.owlutils.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import dev.buha007.owlutils.Main;
import dev.buha007.owlutils.Var;

public class StaffChat implements Listener {

    public StaffChat() {
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if (!e.getPlayer().hasPermission("owl.staff.chat"))
            return;

        String message = e.getMessage();
        if (message.charAt(0) != Var.STAFF_CHAT_CHAR)
            return;

        e.setCancelled(true);

        String staffMessage = Var.STAFF_CHAT_FORMAT;
        staffMessage = staffMessage.replace("{MESSAGE}", message.replaceFirst("" + Var.STAFF_CHAT_CHAR, ""));
        staffMessage = staffMessage.replace("{PLAYER}", e.getPlayer().getName());

        for (Player p : Var.STAFF_CHAT_USERS) {
            Main.msg(p, staffMessage);
        }
    }

}