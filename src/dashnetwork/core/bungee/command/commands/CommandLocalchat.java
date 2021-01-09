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

public class CommandLocalchat extends CoreCommand {

    public CommandLocalchat() {
        super(true, PermissionType.OWNER, "localchat", "globalchat", "lc");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            List<ProxiedPlayer> targets = new ArrayList<>();

            if (args.length > 0) {
                targets.addAll(SelectorUtils.getPlayers(sender, args[0]));

                if (targets.isEmpty()) {
                    Messages.noPlayerFound(sender);
                    return;
                }
            } else
                targets.add(player);

            List<ProxiedPlayer> added = new ArrayList<>();
            List<ProxiedPlayer> removed = new ArrayList<>();

            for (ProxiedPlayer target : targets) {
                User user = User.getUser(target);
                boolean localchat = !user.inLocalChat();

                user.setLocalChat(localchat);

                if (localchat) {
                    MessageUtils.message(target, "&6&l» &7You are now in LocalChat");

                    if (!target.equals(player))
                        added.add(target);
                } else {
                    MessageUtils.message(target, "&6&l» &7You are no longer in LocalChat");

                    if (!target.equals(player))
                        removed.add(target);
                }
            }

            if (!added.isEmpty())
                Messages.targetNowIn(sender, added, "LocalChat");

            if (!removed.isEmpty())
                Messages.targetNoLongerIn(sender, removed, "LocalChat");
        } else
            Messages.playersOnly();
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return CompletionUtils.players(sender, args[0]);
        return Collections.EMPTY_LIST;
    }

}
