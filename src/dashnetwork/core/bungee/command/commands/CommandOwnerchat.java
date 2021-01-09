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

public class CommandOwnerchat extends CoreCommand {

    public CommandOwnerchat() {
        super(true, PermissionType.OWNER, "ownerchat", "oc", "dc");
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
                boolean ownerchat = !user.inOwnerChat();

                user.setOwnerChat(ownerchat);

                if (ownerchat) {
                    MessageUtils.message(target, "&6&l» &7You are now in OwnerChat");

                    if (!target.equals(player))
                        added.add(target);
                } else {
                    MessageUtils.message(target, "&6&l» &7You are no longer in OwnerChat");

                    if (!target.equals(player))
                        removed.add(target);
                }
            }

            if (!added.isEmpty())
                Messages.targetNowIn(sender, added, "OwnerChat");

            if (!removed.isEmpty())
                Messages.targetNoLongerIn(sender, removed, "OwnerChat");
        } else
            MessageUtils.broadcast(PermissionType.OWNER, "&9&lOwner&6 Console &6&l>&c " + StringUtils.unsplit(args, ' '));
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return CompletionUtils.players(sender, args[0]);
        return Collections.EMPTY_LIST;
    }

}
