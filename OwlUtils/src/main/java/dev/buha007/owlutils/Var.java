package dev.buha007.owlutils;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Var {
    public static char STAFF_CHAT_CHAR = '@';
    public static String STAFF_CHAT_FORMAT = "";
    public static String MENTION_FORMAT = "";
    public static Sound SOUND = null;
    public static int CAPS = 0;
    public static int EXTENSION = 0;
    public static String CONSOLE_PLAYER_LIST_FORMAT = "";
    public static String CONSOLE_PLAYER_LIST_SEPARATOR = "";
    public static Long RANDOM_TP_DELAY = 0L;
    public static int RANDOM_TP_MIN_RANGE = 0;
    public static int RANDOM_TP_MAX_RANGE = 0;
    public static int RANDOM_TP_TRY_AMOUNT = 0;
    public static String RANDOM_TP_WORLD = "";
    public static ArrayList<Player> STAFF_CHAT_USERS = new ArrayList<Player>();
    public static ArrayList<Player> NOTIFY_BREAK_USERS = new ArrayList<Player>();
    public static ArrayList<String> BLOCKED_NICKS = new ArrayList<String>();

    public static void load(FileConfiguration config) {
        try {
            SOUND = Sound.valueOf(config.getString("mention-notifier.sound"));
        } catch (IllegalArgumentException exception) {
            Main.msg(Bukkit.getServer().getConsoleSender(),
                    "&8[&4ERROR&8] &2Owl&bUtils &8-> &cno sound specified in config at mention-notifier");
        }
        STAFF_CHAT_CHAR = config.getString("staff-chat.first-char").charAt(0);
        CAPS = config.getInt("caps-control.caps-letters") + 1;
        EXTENSION = config.getInt("caps-control.extension-letters") + 1;
        CONSOLE_PLAYER_LIST_FORMAT = config.getString("console-player-list.format");
        CONSOLE_PLAYER_LIST_SEPARATOR = config.getString("console-player-list.separator");
        MENTION_FORMAT = config.getString("mention-notifier.notification");
        STAFF_CHAT_FORMAT = config.getString("staff-chat.format");
        RANDOM_TP_DELAY = config.getLong("random-teleport.delay-between-teleports") * 1000;
        RANDOM_TP_MIN_RANGE = config.getInt("random-teleport.range.min");
        RANDOM_TP_MAX_RANGE = config.getInt("random-teleport.range.max");
        RANDOM_TP_TRY_AMOUNT = config.getInt("random-teleport.tryAmountToTeleport");
        RANDOM_TP_WORLD = config.getString("random-teleport.enabledWorld");
        BLOCKED_NICKS.clear();
        for (String nick : config.getStringList("blocked-nicks.list")) {
            BLOCKED_NICKS.add(nick.toLowerCase());
        }
    }

}