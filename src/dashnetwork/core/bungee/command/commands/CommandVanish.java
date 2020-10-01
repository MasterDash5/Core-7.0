package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.*;
import dashnetwork.core.utils.ListUtils;
import dashnetwork.core.utils.MessageBuilder;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandVanish extends CoreCommand {

    public CommandVanish() {
        super(true, PermissionType.STAFF, "vanish", "v");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        List<ProxiedPlayer> targets = new ArrayList<>();

        if (args.length > 0 && PermissionType.ADMIN.hasPermission(sender))
            targets.addAll(SelectorUtils.getPlayers(sender, args[0]));
        else if (sender instanceof ProxiedPlayer)
            targets.add((ProxiedPlayer) sender);
        else {
            MessageUtils.message(sender, "&6&l» &7/vanish <player>");
            return;
        }

        List<ProxiedPlayer> added = new ArrayList<>();
        List<ProxiedPlayer> removed = new ArrayList<>();

        for (ProxiedPlayer target : targets) {
            User user = User.getUser(target);
            boolean vanished = !user.isVanished();

            user.setVanished(vanished);

            if (vanished) {
                MessageUtils.message(target, "&6&l» &7You are now vanished");

                if (!target.equals(sender))
                    added.add(target);
            } else {
                MessageUtils.message(target, "&6&l» &7You are no longer vanished");

                if (!target.equals(sender))
                    removed.add(target);
            }
        }

        if (!added.isEmpty()) {
            String displaynames = ListUtils.fromList(NameUtils.toDisplayNames(added), false, false);
            String names = ListUtils.fromList(NameUtils.toNames(added), false, false);

            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» ");
            message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
            message.append("&7 " + (added.size() > 1 ? "are" : "is") + " now vanished");

            sender.sendMessage(message.build());
        }

        if (!removed.isEmpty()) {
            String displaynames = ListUtils.fromList(NameUtils.toDisplayNames(removed), false, false);
            String names = ListUtils.fromList(NameUtils.toNames(removed), false, false);

            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» ");
            message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
            message.append("&7 " + (removed.size() > 1 ? "are" : "is") + " no longer vanished");

            sender.sendMessage(message.build());
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1 && PermissionType.ADMIN.hasPermission(sender))
            return NameUtils.toNames(bungee.getPlayers());
        return Collections.EMPTY_LIST;
    }

}
