package xyz.dashnetwork.core.bungee.command.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.utils.*;
import xyz.dashnetwork.core.utils.ProtocolVersion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandSkygrid extends CoreCommand {

    public CommandSkygrid() {
        super(true, PermissionType.STAFF, "skygrid");
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

        Server server = ServerList.getServer("skygrid");
        ProtocolVersion version = ProtocolVersion.fromId(server.getVersion());
        List<ProxiedPlayer> moved = new ArrayList<>();

        for (ProxiedPlayer target : targets) {
            User user = User.getUser(target);

            if (user.getVersion().isNewerThanOrEqual(version)) {
                if (target.equals(sender))
                    Messages.sentToServer(target, server);
                else
                    Messages.forcedToServer(target, NameUtils.getName(sender), NameUtils.getDisplayName(sender), server);

                server.send(target);
            } else
                Messages.serverRequiresVersion(sender, server);
        }

        if (!moved.isEmpty())
            Messages.targetSentToServer(sender, moved, server);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
