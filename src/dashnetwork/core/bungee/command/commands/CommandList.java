package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.NameUtils;
import dashnetwork.core.bungee.utils.PermissionType;
import dashnetwork.core.bungee.utils.User;
import dashnetwork.core.utils.ListUtils;
import dashnetwork.core.utils.MessageBuilder;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.*;

public class CommandList extends CoreCommand {

    public CommandList() {
        super(true, PermissionType.NONE, "list", "glist", "online");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        boolean staff = PermissionType.STAFF.hasPermission(sender);
        Map<String, List<ProxiedPlayer>> players = new HashMap<>();
        int total = 0;


        for (ServerInfo server : bungee.getServers().values()) {
            if (!server.isRestricted() || staff) {
                List<ProxiedPlayer> list = new ArrayList<>();

                for (ProxiedPlayer player : server.getPlayers()) {
                    User user = User.getUser(player);

                    if (!user.isVanished() || staff) {
                        list.add(player);
                        total++;
                    }
                }

                players.put(server.getMotd(), list);
            }
        }

        MessageBuilder message = new MessageBuilder();
        message.append("&6&l» &6" + total + " &7players online");

        for (Map.Entry<String, List<ProxiedPlayer>> entry : players.entrySet()) {
            String name = entry.getKey();
            List<ProxiedPlayer> list = entry.getValue();
            String displaynames = ListUtils.fromListWithColor(NameUtils.toDisplayNames(list), false, false, "&7", "&6");
            String names = ListUtils.fromListWithColor(NameUtils.toNames(list), false, false, "&7", "&6");

            message.append("\n&6&l[" + name + "]: ");
            message.append("&7" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
        }

        sender.sendMessage(message.build());
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
