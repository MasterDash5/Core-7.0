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

public class CommandLobby extends CoreCommand {

    public CommandLobby() {
        super(true, PermissionType.NONE, "lobby", "hub");
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

        List<ProxiedPlayer> moved = new ArrayList<>();

        for (ProxiedPlayer target : targets) {
            if (target.equals(sender))
                MessageUtils.message(target, "&6&l» &7Sending you to &6Lobby");
            else {
                MessageBuilder message = new MessageBuilder();
                message.append("&6&l» ");
                message.append("&6" + NameUtils.getDisplayName(sender)).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + NameUtils.getName(sender));
                message.append("&7 sent you to &6Lobby");

                target.sendMessage(message.build());

                moved.add(target);
            }

            EnumServer.LOBBY.send(target);
        }

        if (!moved.isEmpty()) {
            String displaynames = ListUtils.fromList(NameUtils.toDisplayNames(moved), false, false);
            String names = ListUtils.fromList(NameUtils.toNames(moved), false, false);

            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» ");
            message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
            message.append("&7 " + (moved.size() > 1 ? "were" : "was") + " moved to &6Lobby");

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
