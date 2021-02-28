package dev.buha007.owlutils.commands;

import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.buha007.owlutils.Main;

public class ItemRenameCommand implements CommandExecutor {

    private Main main;

    public ItemRenameCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!cmd.getName().equalsIgnoreCase("rename"))
            return true;

        FileConfiguration cfg = main.getConfig();
        String prefix = cfg.getString("item-rename.prefix");

        if (!sender.hasPermission("owl.item.rename") || !(sender instanceof Player)) {
            Main.msg(sender, prefix + cfg.getString("item-rename.no-perm"));
            return true;
        }

        ItemStack item = ((Player) sender).getInventory().getItemInMainHand();
        if (item == null) {
            Main.msg(sender, prefix + cfg.getString("item-rename.fail"));
            return true;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            Main.msg(sender, prefix + cfg.getString("item-rename.fail"));
            return true;
        }

        String newName = "";
        if (args.length == 0) {
            Map<Enchantment, Integer> enchantments = item.getEnchantments();
            item.setItemMeta(null);
            item.addUnsafeEnchantments(enchantments);
            Main.msg(sender, prefix + cfg.getString("item-rename.success"));
            return true;
        } else {
            int lg = args.length;
            --lg;
            for (int i = 0; i < lg; ++i) {
                newName += args[i];
                newName += " ";
            }
            newName += args[lg];
        }

        meta.setDisplayName(Main.color(newName));
        item.setItemMeta(meta);
        Main.msg(sender, prefix + cfg.getString("item-rename.success"));
        return true;
    }

}