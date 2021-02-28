package dev.buha007.owlutils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import net.md_5.bungee.api.ChatColor;

public class Debug {
    private static void print(String s) {
        Bukkit.getServer().broadcastMessage(s);
    }

    public static void printNumber(String meaning, int x) {
        print(ChatColor.DARK_GREEN + meaning + " " + ChatColor.GREEN + x);
    }

    public static void printLocation(String meaning, Location loc) {
        print(ChatColor.GOLD + meaning + " " + ChatColor.YELLOW + loc.getX() + "  Y: " + loc.getY() + "  Z: "
                + loc.getZ());
    }

    public static void printString(String s) {
        print(ChatColor.GREEN + s);
    }

    public static void printExplicitLocation(String meaning, Location loc) {
        printLocation(meaning, loc);
        print(ChatColor.DARK_GREEN + loc.getBlock().getType().toString());
        loc.setY(loc.getY() + 1);
        print(ChatColor.DARK_GREEN + loc.getBlock().getType().toString());
        loc.setY(loc.getY() + 1);
        print(ChatColor.DARK_GREEN + loc.getBlock().getType().toString() + "\n---");
    }

    public static void newLine() {
        print(ChatColor.DARK_GRAY + "\n--------------------------------------");
    }
}