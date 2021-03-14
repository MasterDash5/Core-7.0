package xyz.dashnetwork.core.bungee.command.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.utils.*;
import xyz.dashnetwork.core.utils.StringUtils;

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
                Messages.noPlayerFound(sender);
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

            if (!added.isEmpty())
                Messages.targetNowIn(sender, added, "StaffChat");

            if (!removed.isEmpty())
                Messages.targetNoLongerIn(sender, removed, "StaffChat");
        } else
            MessageUtils.broadcast(PermissionType.STAFF, "&9&lStaff&6 Console &6&l>&6 " + StringUtils.unsplit(args, ' '));
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1 && PermissionType.ADMIN.hasPermission(sender))
            return CompletionUtils.players(sender, args[0]);
        return Collections.EMPTY_LIST;
    }

}
