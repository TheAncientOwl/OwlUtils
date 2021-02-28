package dev.buha007.owlutils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import dev.buha007.owlutils.Main;
import dev.buha007.owlutils.OwlBroadcaster;
import dev.buha007.owlutils.Var;

public class HelpReloadCommand implements CommandExecutor {

    private Main main;

    public HelpReloadCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("owl"))
            return true;

        if (!sender.hasPermission("owl.utils.use")) {
            Main.msg(sender, "&2&oOwl&b&lUtils &8| &c&oNo permission&8&o!");
            return true;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                main.reloadConfig();
                OwlBroadcaster.getOwlBroadcaster().startOwlBroadcaster(main);
                Var.load(main.getConfig());
                Main.msg(sender, "&2&oOwl&b&oUtils &8&o| &a&oConfig reloaded&8&o!");
                return true;
            } else if (args[0].equalsIgnoreCase("listmessages")) {
                Main.msg(sender, "&8&l---------------------");
                Main.msg(sender, " &8&l<< &2Broadcast messages &8&l>> ");
                Main.msg(sender, "&8&l---------------------");
                FileConfiguration cfg = main.getConfig();
                String prefix = cfg.getString("broadcaster.prefix");
                for (String s : cfg.getStringList("broadcaster.messages")) {
                    Main.msg(sender, prefix + s);
                }
                return true;
            }
        }

        Main.msg(sender, "&8&l---------------");
        Main.msg(sender, " &8&l<< &2Owl&bUtils &eHelp &8&l>> ");
        Main.msg(sender, "&8&l---------------");
        Main.msg(sender, "&8&l[&2&l!&8&l]&bCommands&8:");
        Main.msg(sender, "&8/&2owl &bhelp");
        Main.msg(sender, "&8/&2owl &breload");
        Main.msg(sender, "&8/&2owl &blistmessages &8(&blist messages to broadcast&8)");
        Main.msg(sender, "&8/&2giveall &8(&bwith an item in hand&8)");
        Main.msg(sender, "&8/&2fake &bjoin&8/&bleave &8[&bplayer&8]");
        Main.msg(sender, "&8/&2rename &bnewItemName");
        Main.msg(sender, "&8/&2rename &8(&brestore item's name&8)");
        Main.msg(sender, "&8&l&o>> &7&oOwlUtils developed by Bufnita");

        return true;
    }

}