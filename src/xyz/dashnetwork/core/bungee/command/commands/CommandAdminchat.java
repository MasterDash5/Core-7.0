package xyz.dashnetwork.core.bungee.command.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.utils.*;
import xyz.dashnetwork.core.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandAdminchat extends CoreCommand {

    public CommandAdminchat() {
        super(true, PermissionType.ADMIN, "adminchat", "ac");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            List<ProxiedPlayer> targets = new ArrayList<>();

            if (args.length > 0)
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
                boolean adminchat = !user.inAdminChat();

                user.setAdminChat(adminchat);

                if (adminchat) {
                    MessageUtils.message(target, "&6&l» &7You are now in AdminChat");

                    if (!target.equals(player))
                        added.add(target);
                } else {
                    MessageUtils.message(target, "&6&l» &7You are no longer in AdminChat");

                    if (!target.equals(player))
                        removed.add(target);
                }
            }

            if (!added.isEmpty())
                Messages.targetNowIn(sender, added, "AdminChat");

            if (!removed.isEmpty())
                Messages.targetNoLongerIn(sender, removed, "AdminChat");
        } else
            MessageUtils.broadcast(PermissionType.ADMIN, "&9&lAdmin&6 Console &6&l>&3 " + StringUtils.unsplit(args, ' '));
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return CompletionUtils.players(sender, args[0]);
        return Collections.EMPTY_LIST;
    }

}
