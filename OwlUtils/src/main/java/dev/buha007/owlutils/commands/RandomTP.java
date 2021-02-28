package dev.buha007.owlutils.commands;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import dev.buha007.owlutils.Main;
import dev.buha007.owlutils.Var;

public class RandomTP implements CommandExecutor {
    private static HashMap<UUID, Long> timings;
    Main main;

    public RandomTP(Main instance) {
        timings = new HashMap<UUID, Long>();
        main = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("rtp"))
            return true;
        Player player = (Player) sender;
        Long now = System.currentTimeMillis();

        if (!timings.containsKey(player.getUniqueId())) {
            rtp(player, now, main.getConfig());
            return true;
        }

        if (timings.get(player.getUniqueId()) + Var.RANDOM_TP_DELAY < now
                || player.hasPermission("owl.rtp.bypasscooldown")) {
            rtp(player, now, main.getConfig());
            return true;
        }

        FileConfiguration cfg = main.getConfig();
        String cooldownMessage = cfg.getString("random-teleport.messages.prefix")
                + cfg.getString("random-teleport.messages.cooldown");
        cooldownMessage = cooldownMessage.replace("{time}",
                "" + (timings.get(player.getUniqueId()) + Var.RANDOM_TP_DELAY - now) / 1000);
        Main.msg(player, cooldownMessage);
        return true;
    }

    private void rtp(Player player, Long now, FileConfiguration cfg) {
        World world = player.getWorld();
        if (!world.getName().equals(Var.RANDOM_TP_WORLD)) {
            Main.msg(player, cfg.getString("random-teleport.messages.prefix")
                    + cfg.getString("random-teleport.messages.noPermToTeleportInWorld"));
            return;
        }

        Main.msg(player,
                cfg.getString("random-teleport.messages.prefix") + cfg.getString("random-teleport.messages.wait"));

        WorldBorder worldBorder = world.getWorldBorder();
        Location playerLoc = player.getLocation();
        Random random = new Random();
        for (int i = 1; i <= Var.RANDOM_TP_TRY_AMOUNT; ++i) {
            int rand = getRandom(Var.RANDOM_TP_MIN_RANGE, Var.RANDOM_TP_MAX_RANGE, random);
            double x = (int) (playerLoc.getX()) + rand + 0.5D;
            double z = (int) (playerLoc.getZ()) + rand + 0.5D;
            double y = world.getHighestBlockYAt((int) x, (int) z);

            Location tpLoc = new Location(world, x, y, z, playerLoc.getYaw(), playerLoc.getPitch());

            if (!tpLoc.getBlock().getRelative(BlockFace.DOWN).isLiquid() && worldBorder.isInside(tpLoc)) {
                player.teleport(tpLoc, TeleportCause.COMMAND);
                String msg = cfg.getString("random-teleport.messages.success").replace("{x}", "" + x);
                msg = msg.replace("{y}", "" + y);
                msg = msg.replace("{z}", "" + z);
                Main.msg(player, cfg.getString("random-teleport.messages.prefix") + msg);
                timings.put(player.getUniqueId(), now);
                return;
            }
        }
        Main.msg(player,
                cfg.getString("random-teleport.messages.prefix") + cfg.getString("random-teleport.messages.fail"));
    }

    private int getRandom(int min, int max, Random random) {
        int max2 = 2 * max;
        int rand = random.nextInt(max2) - max;
        int left = min * (-1);
        int safer = 5;
        while (left < rand && rand < min && safer > 0) {
            rand = random.nextInt(max2) - max;
            --safer;
        }
        return rand;
    }

}