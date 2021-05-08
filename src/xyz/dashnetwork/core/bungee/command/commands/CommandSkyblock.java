package xyz.dashnetwork.core.bungee.command.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.utils.*;
import xyz.dashnetwork.core.utils.ProtocolVersion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandSkyblock extends CoreCommand {

    public CommandSkyblock() {
        super(true, PermissionType.NONE, "skyblock");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (PermissionType.ADMIN.hasPermission(sender)) {
            List<ProxiedPlayer> targets = new ArrayList<>();

            if (args.length > 0 && PermissionType.ADMIN.hasPermission(sender))
                targets.addAll(SelectorUtils.getPlayers(sender, args[0]));
            else if (sender instanceof ProxiedPlayer)
                targets.add((ProxiedPlayer) sender);

            if (targets.isEmpty()) {
                Messages.noPlayerFound(sender);
                return;
            }

            Server server = ServerList.getServer("skyblock");
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
        } else
            MessageUtils.message(sender, "&6&l»&c This server is under maintenance");
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
