package xyz.dashnetwork.core.bungee.command.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.utils.*;
import xyz.dashnetwork.core.utils.MessageBuilder;
import xyz.dashnetwork.core.utils.ProtocolVersion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandServer extends CoreCommand {

    public CommandServer() {
        super(true, PermissionType.NONE, "server", "servers");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        List<ProxiedPlayer> targets = new ArrayList<>();
        int length = args.length;
        boolean player = sender instanceof ProxiedPlayer;

        if (length == 0) {
            if (player) {
                User user = User.getUser((ProxiedPlayer) sender);

                MessageBuilder message = new MessageBuilder();
                message.append("&6&l»&7 You are currently on &6" + user.getServer().getName());
                message.append("\n&6&l»&7 Click a server to connect: ");

                for (Server server : ServerList.getServers()) {
                    if (server.getPermission().hasPermission(sender)) {
                        String name = server.getName();
                        int online = server.getPlayers(user.isStaff()).size();

                        if (!message.isEmpty())
                            message.append("&7, ");

                        message.append("&6" + name)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, "&7Click to connect to &6" + name
                                        + "\n&6" + online + "&7 Players")
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + name);
                    }
                }

                MessageUtils.message(sender, message.build());
                return;
            } else
                MessageUtils.message(sender, "&6&l»&7 /server <server> <player>");
        }

        if (length > 1 && PermissionType.ADMIN.hasPermission(sender))
            targets.addAll(SelectorUtils.getPlayers(sender, args[1]));
        else if (player)
            targets.add((ProxiedPlayer) sender);

        if (targets.isEmpty()) {
            Messages.noPlayerFound(sender);
            return;
        }

        Server server = ServerList.getServer(args[0]);

        if (server == null || !server.getPermission().hasPermission(sender)) {
            MessageUtils.message(sender, "&6&l»&7 Couldn't find server &6" + args[0]);
            return;
        }

        ProtocolVersion version = ProtocolVersion.fromId(server.getVersion());
        List<ProxiedPlayer> moved = new ArrayList<>();

        for (ProxiedPlayer target : targets) {
            User user = User.getUser(target);

            if (!server.isBedrock() && user.isBedrock()) {
                MessageUtils.message(target, "&6&l» &cThis server doesn't support &6Bedrock Edition");
                continue;
            }

            if (user.getVersion().isOlderThan(version)) {
                Messages.serverRequiresVersion(target, server);
                continue;
            }

            if (target.equals(sender))
                Messages.sentToServer(target, server);
            else
                Messages.forcedToServer(target, NameUtils.getName(sender), NameUtils.getDisplayName(sender), server);

            server.send(target);
        }

        if (!moved.isEmpty())
            Messages.targetSentToServer(sender, moved, server);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return CompletionUtils.servers(sender, args[0]);
        return Collections.EMPTY_LIST;
    }


}
