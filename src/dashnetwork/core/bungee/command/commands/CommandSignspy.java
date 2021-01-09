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

public class CommandSignspy extends CoreCommand {

    public CommandSignspy() {
        super(true, PermissionType.STAFF, "signspy");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        List<ProxiedPlayer> targets = new ArrayList<>();

        if (args.length > 0 && PermissionType.ADMIN.hasPermission(sender))
            targets.addAll(SelectorUtils.getPlayers(sender, args[0]));
        else if (sender instanceof ProxiedPlayer)
            targets.add((ProxiedPlayer) sender);

        if (targets.isEmpty()) {
            MessageUtils.noPlayerFound(sender);
            return;
        }

        List<ProxiedPlayer> added = new ArrayList<>();
        List<ProxiedPlayer> removed = new ArrayList<>();

        for (ProxiedPlayer target : targets) {
            User user = User.getUser(target);
            boolean signspy = !user.inSignSpy();

            user.setSignSpy(signspy);

            if (signspy) {
                MessageUtils.message(target, "&6&l» &7You are now in SignSpy");

                if (!target.equals(sender))
                    added.add(target);
            } else {
                MessageUtils.message(target, "&6&l» &7You are no longer in SignSpy");

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
            message.append("&7 " + (added.size() > 1 ? "are" : "is") + " now in SignSpy");

            sender.sendMessage(message.build());
        }

        if (!removed.isEmpty()) {
            String displaynames = ListUtils.fromList(NameUtils.toDisplayNames(removed), false, false);
            String names = ListUtils.fromList(NameUtils.toNames(removed), false, false);

            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» ");
            message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
            message.append("&7 " + (removed.size() > 1 ? "are" : "is") + " no longer in SignSpy");

            sender.sendMessage(message.build());
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1 && PermissionType.ADMIN.hasPermission(sender))
            return CompletionUtils.players(sender, args[0]);
        return Collections.EMPTY_LIST;
    }

}
