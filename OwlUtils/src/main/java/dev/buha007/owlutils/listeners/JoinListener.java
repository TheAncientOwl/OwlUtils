package dev.buha007.owlutils.listeners;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import dev.buha007.owlutils.Var;

public class JoinListener implements Listener {
    public JoinListener() {
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        // #============ STAFF CHAT ============# \\
        if (player.hasPermission("owl.staff.chat"))
            Var.STAFF_CHAT_USERS.add(player);

        // #======== CONSOLE PLAYER LIST ========# \\
        String consoleList = Var.CONSOLE_PLAYER_LIST_FORMAT;
        String playerList = "";
        for (Player playerB : Bukkit.getServer().getOnlinePlayers())
            playerList = playerList + playerB.getName() + Var.CONSOLE_PLAYER_LIST_SEPARATOR;
        consoleList = consoleList.replace("{amount}", "" + Bukkit.getServer().getOnlinePlayers().size());
        consoleList = consoleList.replace("{players}", playerList);
        Bukkit.getServer().getLogger().log(Level.INFO, consoleList);
    }

}