package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.MessageUtils;
import dashnetwork.core.bungee.utils.NameUtils;
import dashnetwork.core.bungee.utils.PermissionType;
import dashnetwork.core.bungee.utils.User;
import dashnetwork.core.utils.ColorUtils;
import dashnetwork.core.utils.ListUtils;
import dashnetwork.core.utils.MessageBuilder;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.*;

public class CommandVersionlist extends CoreCommand {

    public CommandVersionlist() {
        super(true, PermissionType.NONE, "versionlist", "verlist");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Map<String, List<ProxiedPlayer>> versionlist = new HashMap<>();
        MessageBuilder message = new MessageBuilder();

        for (User user : User.getUsers(true)) {
            if (!user.isVanished() || user.isStaff()) {
                String version = user.getVersion().getName();
                List<ProxiedPlayer> players = versionlist.getOrDefault(version, new ArrayList<>());

                players.add(user.getPlayer());
                versionlist.put(version, players);
            }
        }

        for (Map.Entry<String, List<ProxiedPlayer>> entry : versionlist.entrySet()) {
            List<ProxiedPlayer> players = entry.getValue();
            List<String> displaynames = NameUtils.toDisplayNames(players);
            List<String> names = NameUtils.toNames(players);

            if (!message.isEmpty())
                message.append("\n");

            message.append("&6&l» &7[&6" + entry.getKey() + "&7] " + ListUtils.fromList(displaynames, false, false)).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + ListUtils.fromList(names, false, false));
        }

        if (message.isEmpty())
            MessageUtils.message(sender, ColorUtils.translate("&6&l» &7Currently no online players"));
        else
            MessageUtils.message(sender, message.build());
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
