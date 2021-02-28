package dev.buha007.owlutils.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import dev.buha007.owlutils.Main;

public class NoSpawnerChange implements Listener {

    private Main main;

    public NoSpawnerChange(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onSpawnerChange(PlayerInteractEvent e) {
        if (!isSpawner(e.getClickedBlock()))
            return;

        Player player = e.getPlayer();
        if (!isSpawnerEgg(player.getInventory().getItemInMainHand()))
            return;

        if (!player.hasPermission("owl.change.spawner")) {
            FileConfiguration cfg = main.getConfig();
            Main.msg(player, cfg.getString("no-spawner-change.prefix") + cfg.getString("no-spawner-change.no-perm"));
            e.setCancelled(true);
            return;
        }

    }

    private final boolean isSpawner(Block block) {
        if (block == null)
            return false;
        Material material = block.getType();
        String name = material == null ? "" : material.toString();
        return (name.equals("SPAWNER"));
    }

    private final boolean isSpawnerEgg(ItemStack item) {
        String name = item == null ? "" : item.getType().toString();
        return (name.endsWith("SPAWN_EGG"));
    }
}