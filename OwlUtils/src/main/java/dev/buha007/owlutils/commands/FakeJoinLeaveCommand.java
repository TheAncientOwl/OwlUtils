package dev.buha007.owlutils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import dev.buha007.owlutils.Main;

public class FakeJoinLeaveCommand implements CommandExecutor {

    private Main main;

    public FakeJoinLeaveCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("fake"))
            return true;

        FileConfiguration cfg = main.getConfig();
        String prefix = cfg.getString("fake.prefix");

        if (!sender.hasPermission("owl.fake.jl")) {
            Main.msg(sender, prefix + cfg.getString("fake.no-perm"));
            return true;
        }

        if (args.length == 0) {
            Main.msg(sender, prefix + cfg.getString("fake.no-args"));
            return true;
        }

        String arg0 = args[0].toLowerCase();
        String message = "";
        if (arg0.equals("join") || arg0.equals("j"))
            message = cfg.getString("fake.join-format");
        else if (arg0.equals("leave") || arg0.equals("l"))
            message = cfg.getString("fake.leave-format");
        else {
            Main.msg(sender, prefix + cfg.getString("fake.wrong-command"));
            return true;
        }

        String playerName = "";
        if (args.length == 1)
            playerName = sender.getName();
        else
            playerName = args[1];

        message = message.replace("{PLAYER}", playerName);
        main.getServer().broadcastMessage(Main.color(message));
        return true;
    }

}