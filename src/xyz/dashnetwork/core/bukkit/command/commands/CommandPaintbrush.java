package xyz.dashnetwork.core.bukkit.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dashnetwork.core.bukkit.command.CoreCommand;
import xyz.dashnetwork.core.bukkit.utils.MessageUtils;
import xyz.dashnetwork.core.bukkit.utils.PermissionType;
import xyz.dashnetwork.core.bukkit.utils.User;

import java.util.Collections;

public class CommandPaintbrush extends CoreCommand {

    public CommandPaintbrush() {
        super(false, PermissionType.OWNER, "paintbrush");
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            int range = 50;

            if (args.length > 0) {
                try {
                    range = Integer.valueOf(args[0]);
                } catch (IllegalArgumentException exception) {
                    MessageUtils.message(sender, "&6&l» &7Not a valid integer.");
                    return;
                }
            }

            Player player = (Player) sender;
            User user = User.getUser(player);
            int paintbrush = user.getPaintbrush();

            if (paintbrush < 0 || range != paintbrush) {
                user.setPaintbrush(range);

                MessageUtils.message(sender, "&6&l» &7Paintbrush range set to &6" + range);
            } else {
                user.setPaintbrush(-1);

                MessageUtils.message(sender, "&6&l» &7Paintbrush disabled");
            }
        } else
            MessageUtils.playersOnly();
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
