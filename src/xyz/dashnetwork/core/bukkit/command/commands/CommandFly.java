package xyz.dashnetwork.core.bukkit.command.commands;

import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dashnetwork.core.bukkit.command.CoreCommand;
import xyz.dashnetwork.core.bukkit.utils.*;
import xyz.dashnetwork.core.utils.MessageBuilder;
import xyz.dashnetwork.core.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandFly extends CoreCommand {

    public CommandFly() {
        super(true, PermissionType.ADMIN, "fly");
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        int length = args.length;
        List<Player> targets = new ArrayList<>();
        boolean force = false;

        if (length > 0)
            targets.addAll(SelectorUtils.getPlayers(sender, args[0]));
        else if (sender instanceof Player)
            targets.add((Player) sender);

        if (length > 1 && args[1].equalsIgnoreCase("-force"))
            force = true;

        if (targets.isEmpty()) {
            MessageUtils.message(sender, "&6&l» &7/fly <player>");
            return;
        }

        List<Player> added = new ArrayList<>();
        List<Player> removed = new ArrayList<>();

        for (Player target : targets) {
            User user = User.getUser(target);
            boolean flight = !target.getAllowFlight();
            MessageBuilder message = new MessageBuilder();

            target.setAllowFlight(flight);

            if (force)
                target.setFlying(true);

            message.append("&6&l» &7Set fly mode");

            if (flight) {
                message.append("&a enabled &7for ");

                if (!target.equals(sender))
                    added.add(target);
            } else {
                message.append("&c disabled &7for ");

                if (!target.equals(sender))
                    removed.add(target);
            }

            message.append("&6" + user.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + target.getName());

            MessageUtils.message(target, message.build());
        }

        if (!added.isEmpty()) {
            String displaynames = StringUtils.fromList(NameUtils.toDisplayNames(added), false, false);
            String names = StringUtils.fromList(NameUtils.toNames(added), false, false);

            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» &7Set fly mode &aenabled &7for ");
            message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);

            MessageUtils.message(sender, message.build());
        }

        if (!removed.isEmpty()) {
            String displaynames = StringUtils.fromList(NameUtils.toDisplayNames(removed), false, false);
            String names = StringUtils.fromList(NameUtils.toNames(removed), false, false);

            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» &7Set fly mode &cdisabled &7for ");
            message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);

            MessageUtils.message(sender, message.build());
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1)
            return CompletionUtils.players(sender, args[0]);
        return Collections.EMPTY_LIST;
    }

}
