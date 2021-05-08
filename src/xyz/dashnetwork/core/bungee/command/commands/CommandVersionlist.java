package xyz.dashnetwork.core.bungee.command.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.utils.MessageUtils;
import xyz.dashnetwork.core.bungee.utils.NameUtils;
import xyz.dashnetwork.core.bungee.utils.PermissionType;
import xyz.dashnetwork.core.bungee.utils.User;
import xyz.dashnetwork.core.utils.MessageBuilder;
import xyz.dashnetwork.core.utils.StringUtils;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class CommandVersionlist extends CoreCommand {

    public CommandVersionlist() {
        super(true, PermissionType.NONE, "versionlist", "verlist");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Map<String, List<ProxiedPlayer>> versionlist = new HashMap<>();
        MessageBuilder message = new MessageBuilder();

        for (User user : User.getUsers(true)) {
            if (!user.isVanished() || PermissionType.STAFF.hasPermission(sender)) {
                String version;

                if (user.isBedrock())
                    version = "Bedrock";
                else
                    version = user.getVersion().getName();

                List<ProxiedPlayer> players = versionlist.getOrDefault(version, new ArrayList<>());
                players.add(user.getPlayer());
                versionlist.put(version, players);
            }
        }

        for (Map.Entry<String, List<ProxiedPlayer>> entry : versionlist.entrySet()) {
            List<ProxiedPlayer> players = entry.getValue();
            List<String> displaynamesList = new CopyOnWriteArrayList<>(NameUtils.toDisplayNames(players));

            for (int i = 0; i < players.size(); i++)
                displaynamesList.set(i, displaynamesList.get(i) + "&7");

            if (!message.isEmpty())
                message.append("\n");

            message.append("&6&l» &6&l[" + entry.getKey() + "] ");
            message.append("&7" + StringUtils.fromList(displaynamesList, false, false))
                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + StringUtils.fromList(NameUtils.toNames(players), false, false));
        }

        if (message.isEmpty())
            MessageUtils.message(sender, "&6&l» &7Currently no online players");
        else
            MessageUtils.message(sender, message.build());
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
