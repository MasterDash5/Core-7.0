package xyz.dashnetwork.core.bungee.command.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.utils.*;
import xyz.dashnetwork.core.utils.MessageBuilder;
import xyz.dashnetwork.core.utils.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class CommandList extends CoreCommand {

    public CommandList() {
        super(true, PermissionType.NONE, "list", "glist", "online");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        boolean staff = PermissionType.STAFF.hasPermission(sender);
        Map<String, List<ProxiedPlayer>> players = new HashMap<>();
        int total = 0;

        for (Server server : ServerList.getServers()) {
            if (server.getPermission().hasPermission(sender)) {
                List<ProxiedPlayer> list = server.getPlayers(staff);

                players.put(server.getName(), list);
                total += list.size();
            }
        }

        MessageBuilder message = new MessageBuilder();
        message.append("&6&l» &6" + total + " &7players online");

        for (Map.Entry<String, List<ProxiedPlayer>> entry : players.entrySet()) {
            String name = entry.getKey();
            List<ProxiedPlayer> list = entry.getValue();
            List<String> displaynamesList = new CopyOnWriteArrayList<>(NameUtils.toDisplayNames(list));

            for (int i = 0; i < list.size(); i++)
                displaynamesList.set(i, displaynamesList.get(i) + "&7");

            String displaynames = StringUtils.fromList(displaynamesList, false, false);
            String names = StringUtils.fromList(NameUtils.toNames(list), false, false);

            message.append("\n&6&l» &6" + name + ": ");
            message.append("&7" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
        }

        MessageUtils.message(sender, message.build());
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
