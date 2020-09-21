package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.MessageUtils;
import dashnetwork.core.bungee.utils.PermissionType;
import dashnetwork.core.bungee.utils.User;
import dashnetwork.core.utils.ColorUtils;
import dashnetwork.core.utils.ListUtils;
import dashnetwork.core.utils.MessageBuilder;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class CommandVersionlist extends CoreCommand {

    public CommandVersionlist() {
        super(true, PermissionType.NONE, "versionlist", "verlist");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Map<String, List<String>> versionlist = new HashMap<>();
        MessageBuilder message = new MessageBuilder();

        for (User user : User.getUsers()) {
            if (!user.isVanished() || user.isStaff()) {
                String version = user.getVersion().getName();
                List<String> players = versionlist.getOrDefault(version, new ArrayList<>());

                players.add(user.getPlayer().getUniqueId().toString());
                versionlist.put(version, players);
            }
        }

        for (Map.Entry<String, List<String>> entry : versionlist.entrySet()) {
            List<String> displaynames = new ArrayList<>();
            List<String> names = new ArrayList<>();

            for (String uuid : entry.getValue()) {
                Player target = Bukkit.getPlayer(UUID.fromString(uuid));
                displaynames.add(target.getDisplayName());
                names.add(target.getName());
            }

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
