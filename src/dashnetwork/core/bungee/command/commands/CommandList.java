package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.*;
import dashnetwork.core.utils.MessageBuilder;
import dashnetwork.core.utils.StringUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.*;
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

        for (EnumServer server : EnumServer.values()) {
            if (!server.getPermission().hasPermission(sender)) {
                List<ProxiedPlayer> list = new ArrayList<>();

                for (ProxiedPlayer player : server.getPlayers(!staff)) {
                    User user = User.getUser(player);

                    if (!user.isVanished() || staff) {
                        list.add(player);
                        total++;
                    }
                }

                players.put(server.getName(), list);
            }
        }

        MessageBuilder message = new MessageBuilder();
        message.append("&6&l» &6" + total + " &7players online");

        for (Map.Entry<String, List<ProxiedPlayer>> entry : players.entrySet()) {
            String name = entry.getKey();
            List<ProxiedPlayer> list = entry.getValue();
            List<String> displaynamesList = new CopyOnWriteArrayList<>(NameUtils.toDisplayNames(list));
            List<String> namesList = new CopyOnWriteArrayList<>(NameUtils.toNames(list));

            for (int i = 0; i < list.size(); i++) {
                displaynamesList.set(i, displaynamesList.get(i) + "&7");
                namesList.set(i, namesList.get(i) + "&7");
            }

            String displaynames = StringUtils.fromList(NameUtils.toDisplayNames(list), false, false);
            String names = StringUtils.fromList(NameUtils.toNames(list), false, false);

            message.append("\n&6&l[" + name + "]: ");
            message.append("&7" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
        }

        MessageUtils.message(sender, message.build());
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
