package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.*;
import dashnetwork.core.utils.ListUtils;
import dashnetwork.core.utils.MessageBuilder;
import dashnetwork.core.utils.StringUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandStaffchat extends CoreCommand {

    public CommandStaffchat() {
        super(true, PermissionType.STAFF, "staffchat", "sc");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            List<ProxiedPlayer> targets = new ArrayList<>();

            if (args.length > 0 && PermissionType.ADMIN.hasPermission(sender))
                targets.addAll(SelectorUtils.getPlayers(sender, args[0]));
            else
                targets.add(player);

            if (targets.isEmpty()) {
                MessageUtils.noPlayerFound(sender);
                return;
            }

            List<ProxiedPlayer> added = new ArrayList<>();
            List<ProxiedPlayer> removed = new ArrayList<>();

            for (ProxiedPlayer target : targets) {
                User user = User.getUser(target);
                boolean staffchat = !user.inStaffChat();

                user.setStaffChat(staffchat);

                if (staffchat) {
                    MessageUtils.message(target, "&6&l» &7You are now in StaffChat");

                    if (!target.equals(player))
                        added.add(target);
                } else {
                    MessageUtils.message(target, "&6&l» &7You are no longer in StaffChat");

                    if (!target.equals(player))
                        removed.add(target);
                }
            }

            if (!added.isEmpty()) {
                String displaynames = ListUtils.fromList(NameUtils.toDisplayNames(added), false, false);
                String names = ListUtils.fromList(NameUtils.toNames(added), false, false);

                MessageBuilder message = new MessageBuilder();
                message.append("&6&l» ");
                message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
                message.append("&7 " + (added.size() > 1 ? "are" : "is") + " now in StaffChat");

                player.sendMessage(message.build());
            }

            if (!removed.isEmpty()) {
                String displaynames = ListUtils.fromList(NameUtils.toDisplayNames(removed), false, false);
                String names = ListUtils.fromList(NameUtils.toNames(removed), false, false);

                MessageBuilder message = new MessageBuilder();
                message.append("&6&l» ");
                message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
                message.append("&7 " + (removed.size() > 1 ? "are" : "is") + " no longer in StaffChat");

                player.sendMessage(message.build());
            }
        } else
            MessageUtils.broadcast(PermissionType.STAFF, "&9&lStaff&6 Console &6&l>&6 " + StringUtils.unsplit(args, ' '));
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1 && PermissionType.ADMIN.hasPermission(sender))
            return CompletionUtils.players(args[0]);
        return Collections.EMPTY_LIST;
    }

}
