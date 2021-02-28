package dev.buha007.owlutils.listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import dev.buha007.owlutils.Main;
import dev.buha007.owlutils.Var;

public class OreAnouncer implements Listener {

    private Main main;

    public OreAnouncer(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (player.hasPermission("owl.break.notify"))
            Var.NOTIFY_BREAK_USERS.add(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (player.hasPermission("owl.break.notify"))
            Var.NOTIFY_BREAK_USERS.remove(player);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (Var.NOTIFY_BREAK_USERS.size() == 0)
            return;

        Player player = e.getPlayer();
        if (player.hasPermission("owl.break.incognito"))
            return;

        FileConfiguration cfg = main.getConfig();

        String block = e.getBlock().getType().toString();

        for (String b : cfg.getStringList("ore-anouncer.blocks-to-anounce"))
            if (b.equals(block)) {
                String notification = cfg.getString(("ore-anouncer.notification"));
                notification = notification.replace("{PLAYER}", player.getName());
                notification = notification.replace("{BLOCK}", block.toLowerCase().replace("_", " "));

                for (Player user : Var.NOTIFY_BREAK_USERS)
                    Main.msg(user, notification);

                break;
            }
    }
}