package dev.buha007.owlutils;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import dev.buha007.owlutils.commands.FakeJoinLeaveCommand;
import dev.buha007.owlutils.commands.GiveAll;
import dev.buha007.owlutils.commands.HelpReloadCommand;
import dev.buha007.owlutils.commands.ItemRenameCommand;
import dev.buha007.owlutils.commands.RandomTP;
import dev.buha007.owlutils.listeners.BlockNicks;
import dev.buha007.owlutils.listeners.CapsControl;
import dev.buha007.owlutils.listeners.JoinListener;
import dev.buha007.owlutils.listeners.MentionNotifier;
import dev.buha007.owlutils.listeners.MergeCapsMention;
import dev.buha007.owlutils.listeners.NoSpawnerChange;
import dev.buha007.owlutils.listeners.OreAnouncer;
import dev.buha007.owlutils.listeners.QuitListener;
import dev.buha007.owlutils.listeners.StaffChat;
import net.md_5.bungee.api.ChatColor;

/**
 * @author Bufnita
 */
public class Main extends JavaPlugin {
    public ConsoleCommandSender console;
    public Server server;
    private PluginManager pm;
    private ConfigAccessor configAccessor;

    @Override
    public void onEnable() {
        configAccessor = new ConfigAccessor(this, "config.yml");
        configAccessor.saveDefaultConfig();
        FileConfiguration cfg = configAccessor.getConfig();

        server = this.getServer();
        console = server.getConsoleSender();
        pm = getServer().getPluginManager();

        getCommand("owl").setExecutor(new HelpReloadCommand(this));
        getCommand("fake").setExecutor(new FakeJoinLeaveCommand(this));
        getCommand("rename").setExecutor(new ItemRenameCommand(this));
        getCommand("giveall").setExecutor(new GiveAll(this));

        if (cfg.getBoolean("optimizeCapsControlAndMentionNotifier")) {
            pm.registerEvents(new MergeCapsMention(), this);
        } else {
            if (cfg.getBoolean("caps-control.enabled"))
                pm.registerEvents(new CapsControl(this), this);

            if (cfg.getBoolean("mention-notifier.enabled"))
                pm.registerEvents(new MentionNotifier(), this);
        }

        if (cfg.getBoolean("blocked-nicks.enabled")) {
            pm.registerEvents(new BlockNicks(this), this);
        }

        if (cfg.getBoolean("no-spawner-change.enabled"))
            pm.registerEvents(new NoSpawnerChange(this), this);

        if (cfg.getBoolean("ore-anouncer.enabled")) {
            Var.NOTIFY_BREAK_USERS.clear();
            for (Player p : server.getOnlinePlayers())
                if (p.hasPermission("owl.break.notify"))
                    Var.NOTIFY_BREAK_USERS.add(p);
            pm.registerEvents(new OreAnouncer(this), this);
        }

        if (cfg.getBoolean("random-teleport.enabled")) {
            getCommand("rtp").setExecutor(new RandomTP(this));
        }

        Var.STAFF_CHAT_USERS.clear();
        for (Player p : server.getOnlinePlayers())
            if (p.hasPermission("owl.staff.chat"))
                Var.STAFF_CHAT_USERS.add(p);
        pm.registerEvents(new StaffChat(), this);

        if (cfg.getBoolean("broadcaster.enabled"))
            OwlBroadcaster.getOwlBroadcaster().startOwlBroadcaster(this);

        pm.registerEvents(new JoinListener(), this);
        pm.registerEvents(new QuitListener(), this);

        Var.load(configAccessor.getConfig());

        Main.msg(console, "OwlUtils developed by Bufnita");
    }

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void msg(Player player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void msg(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    @Override
    public FileConfiguration getConfig() {
        return configAccessor.getConfig();
    }

    @Override
    public void reloadConfig() {
        configAccessor.reloadConfig();
    }

    @Override
    public void onDisable() {

    }

}