package dev.buha007.owlutils.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import dev.buha007.owlutils.Main;
import dev.buha007.owlutils.Var;

public class CapsControl implements Listener {

    public CapsControl(Main main) {
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        if (e.getPlayer().hasPermission("owl.caps.bypass"))
            return;

        String message = e.getMessage();

        String newMessage = "";
        for (String word : message.split(" ")) {
            if (isPlayer(word)) {
                newMessage += word;
                newMessage += " ";
                continue;
            }

            String newWord = "";

            char[] sNormal = word.toCharArray();
            char[] sLower = word.toLowerCase().toCharArray();
            int sz = sNormal.length;
            int nrCaps = 0;
            char lastChar = '.';
            int nra = 0;
            for (int i = 0; i < sz; ++i) {
                if ('A' <= sNormal[i] && sNormal[i] <= 'Z')
                    ++nrCaps;

                if (sLower[i] != lastChar) {
                    nra = 1;
                    lastChar = sLower[i];
                } else if (sLower[i] == lastChar) {
                    ++nra;
                }

                if (nra < Var.EXTENSION)
                    newWord += sNormal[i];
            }

            if (nrCaps >= Var.CAPS)
                newWord = newWord.toLowerCase();
            newMessage += newWord;
            newMessage += " ";
        }

        e.setMessage(newMessage);
    }

    private final boolean isPlayer(String str) {
        return (Bukkit.getPlayerExact(str) != null);
    }

}