package xyz.dashnetwork.core.bukkit.command.commands;

import org.bukkit.DyeColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.dashnetwork.core.bukkit.Core;
import xyz.dashnetwork.core.bukkit.command.CoreCommand;
import xyz.dashnetwork.core.bukkit.utils.MessageUtils;
import xyz.dashnetwork.core.bukkit.utils.PermissionType;
import xyz.dashnetwork.core.bukkit.utils.RandomUtils;
import xyz.dashnetwork.core.bukkit.utils.User;

import java.util.Collections;
import java.util.List;

public class CommandWolfpack extends CoreCommand {

    public CommandWolfpack() {
        super(false, PermissionType.OWNER, "wolfpack");
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (Core.getServerName().equalsIgnoreCase("pvp")) {
            MessageUtils.message(sender, "&6&l» &7No cheating on my pvp server.");
            return;
        }

        if (sender instanceof Player) {
            int length = args.length;
            int size = 200;

            if (length > 0) {
                try {
                    size = Integer.valueOf(args[0]);
                } catch (IllegalArgumentException exception) {
                    MessageUtils.message(sender, "&6&l» &7Not a valid integer");
                    return;
                }
            }

            Player player = (Player) sender;
            User user = User.getUser(player);
            List<Wolf> wolfpack = user.getWolfpack();

            if (wolfpack.isEmpty()) {
                for (int i = 0; i < size; i++) {
                    Wolf wolf = (Wolf) player.getWorld().spawnEntity(player.getLocation(), EntityType.WOLF);
                    wolf.setTamed(true);
                    wolf.setOwner(player);
                    wolf.setCollarColor(RandomUtils.randomDyeColor());
                    wolf.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 255, true, false));

                    wolfpack.add(wolf);
                }

                MessageUtils.message(sender, "&6&l» &7The dogs have been summoned");
            } else {
                for (Wolf wolf : wolfpack)
                    wolf.remove();

                wolfpack.clear();

                MessageUtils.message(sender, "&6&l» &7Rip doggies");
            }
        } else
            MessageUtils.playersOnly();
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return Collections.EMPTY_LIST;
    }
}
