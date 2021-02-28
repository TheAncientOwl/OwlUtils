package dev.buha007.owlutils.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import dev.buha007.owlutils.Main;
import dev.buha007.owlutils.Var;

public class BlockNicks implements Listener {

    Main main;

    public BlockNicks(Main instance) {
        main = instance;
    }

    @EventHandler
    public void onNickCommand(PlayerCommandPreprocessEvent e) {

        String message = e.getMessage().toLowerCase();

        if (!message.contains("nick") || e.getPlayer().hasPermission("owl.nicks.bypass"))
            return;

        for (String nick : Var.BLOCKED_NICKS) {
            if (contains(message.toCharArray(), nick.toCharArray())) {
                Main.msg(e.getPlayer(), main.getConfig().getString("blocked-nicks.denyMessage"));
                e.setCancelled(true);
                return;
            }
        }

    }

    /**
     * Check if s1 and s2 have a common substring
     * 
     * @param message charArray
     * @param nick    charArray
     * @return true/false
     */
    private boolean contains(char message[], char nick[]) {
        int iM = 0;// index for message
        int iN = 0;// index for nick

        while (iM < message.length && iN < nick.length) {

            if (message[iM] == nick[iN]) {
                ++iM;
                ++iN;
            } else
                ++iM;
        }

        return (iN == nick.length);
    }

}