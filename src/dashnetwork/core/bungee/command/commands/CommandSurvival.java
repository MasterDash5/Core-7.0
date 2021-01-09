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

public class CommandSurvival extends CoreCommand {

    public CommandSurvival() {
        super(true, PermissionType.NONE, "survival");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        List<ProxiedPlayer> targets = new ArrayList<>();

        if (args.length > 0 && PermissionType.ADMIN.hasPermission(sender))
            targets.addAll(SelectorUtils.getPlayers(sender, args[0]));
        else if (sender instanceof ProxiedPlayer)
            targets.add((ProxiedPlayer) sender);

        if (targets.isEmpty()) {
            Messages.noPlayerFound(sender);
            return;
        }

        EnumServer server = EnumServer.SURVIVAL;
        List<ProxiedPlayer> moved = new ArrayList<>();

        for (ProxiedPlayer target : targets) {
            if (target.equals(sender))
                Messages.sentToServer(target, server);
            else
                Messages.forcedToServer(target, NameUtils.getName(sender), NameUtils.getDisplayName(sender), server);
        }

        if (!moved.isEmpty())
            Messages.targetSentToServer(sender, moved, server);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1 && PermissionType.ADMIN.hasPermission(sender))
            return CompletionUtils.players(sender, args[0]);
        return Collections.EMPTY_LIST;
    }

}
