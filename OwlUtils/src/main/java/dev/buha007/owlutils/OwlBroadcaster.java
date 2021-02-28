package dev.buha007.owlutils;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class OwlBroadcaster {
    private static OwlBroadcaster broadcaster;
    private BukkitTask task;
    private String prefix = "";
    private List<String> messages;
    private int messageIndex = 0;
    private int interval = 30;

    private OwlBroadcaster() {
    }

    public static OwlBroadcaster getOwlBroadcaster() {
        if (broadcaster == null)
            broadcaster = new OwlBroadcaster();
        return broadcaster;
    }

    public void startOwlBroadcaster(Main main) {
        FileConfiguration cfg = main.getConfig();
        prefix = cfg.getString("broadcaster.prefix");
        messages = cfg.getStringList("broadcaster.messages");
        interval = cfg.getInt("broadcaster.interval");
        int lastMessageIndex = messages.size() - 1;
        messageIndex = 0;

        if (task != null) {
            task.cancel();
        }

        if (lastMessageIndex == -1) {
            Main.msg(main.console, "&c========================================");
            Main.msg(main.console, " ");
            Main.msg(main.console,
                    "&cHEY! &2Owl&bUtils &cno messages in config! &7check it and reload owl utils &c(/owl reload)");
            Main.msg(main.console, " ");
            Main.msg(main.console, "&c========================================");
            return;
        }

        task = new BukkitRunnable() {
            public void run() {
                String message = Main.color(prefix + (String) messages.get(messageIndex));
                for (Player player : main.server.getOnlinePlayers())
                    player.sendMessage(message);
                if (messageIndex == lastMessageIndex)
                    messageIndex = 0;
                else
                    messageIndex += 1;
            }
        }.runTaskTimerAsynchronously(main, 0L, 20L * interval);
    }

}