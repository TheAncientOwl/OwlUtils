package dev.buha007.owlutils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import dev.buha007.owlutils.Main;

public class GiveAll implements CommandExecutor {

    private Main main;

    public GiveAll(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("giveall"))
            return true;

        FileConfiguration cfg = main.getConfig();

        if (!sender.hasPermission("owl.give.all") || !(sender instanceof Player)) {
            Main.msg(sender, cfg.getString("giveall.prefix") + cfg.getString("giveall.no-perm"));
            return true;
        }

        ItemStack item = ((Player) sender).getInventory().getItemInMainHand();

        String message = cfg.getString("giveall.message");
        message = message.replace("{ITEM}", item.getType().toString().toLowerCase().replace("_", " "));
        message = message.replace("{AMOUNT}", "" + item.getAmount());

        Player pSender = (Player) sender;
        for (Player p : main.server.getOnlinePlayers())
            if (p != pSender) {
                p.getInventory().addItem(item);
                Main.msg(p, message);
            }
        Main.msg(sender, cfg.getString("giveall.prefix") + cfg.getString("giveall.success"));
        return true;
    }

}