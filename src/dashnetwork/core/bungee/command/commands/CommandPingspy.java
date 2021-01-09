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

public class CommandPingspy extends CoreCommand {

    public CommandPingspy() {
        super(true, PermissionType.ADMIN, "pingspy");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        List<ProxiedPlayer> targets = new ArrayList<>();

        if (args.length > 0)
            targets.addAll(SelectorUtils.getPlayers(sender, args[0]));
        else if (sender instanceof ProxiedPlayer)
            targets.add((ProxiedPlayer) sender);

        if (targets.isEmpty()) {
            Messages.noPlayerFound(sender);
            return;
        }

        List<ProxiedPlayer> added = new ArrayList<>();
        List<ProxiedPlayer> removed = new ArrayList<>();

        for (ProxiedPlayer target : targets) {
            User user = User.getUser(target);
            boolean pingspy = !user.inPingSpy();

            user.setPingSpy(pingspy);

            if (pingspy) {
                MessageUtils.message(target, "&6&l» &7You are now in PingSpy");

                if (!target.equals(sender))
                    added.add(target);
            } else {
                MessageUtils.message(target, "&6&l» &7You are no longer in PingSpy");

                if (!target.equals(sender))
                    removed.add(target);
            }
        }

        if (!added.isEmpty())
            Messages.targetNowIn(sender, added, "AdminChat");

        if (!removed.isEmpty())
            Messages.targetNoLongerIn(sender, removed, "AdminChat");
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return CompletionUtils.players(sender, args[0]);
        return Collections.EMPTY_LIST;
    }

}
