package dashnetwork.core.bungee.utils;

import dashnetwork.core.utils.MojangUtils;
import dashnetwork.core.utils.Username;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class NameUtils {

    private static BungeeCord bungee = BungeeCord.getInstance();

    public static String getDisplayName(CommandSender sender) {
        if (sender instanceof ProxiedPlayer)
            return User.getUser((ProxiedPlayer) sender).getDisplayName();
        else if (sender.equals(bungee.getConsole()))
            return "Console";

        return sender.getName();
    }

    public static String getName(CommandSender sender) {
        if (sender.equals(bungee.getConsole()))
            return "Console";

        return sender.getName();
    }

    public static List<String> toNames(Collection<ProxiedPlayer> players) {
        List<String> names = new ArrayList<>();

        for (ProxiedPlayer player : players)
            names.add(player.getName());

        return names;
    }


    public static List<String> fromUuids(Collection<String> uuids) {
        List<String> names = new ArrayList<>();

        for (String uuid : uuids) {
            String name = DataUtils.getNames().get(uuid);

            if (name == null) {
                Username[] usernames = MojangUtils.getNameHistoryFromUuid(UUID.fromString(uuid));

                if (usernames == null)
                    name = uuid;
                else
                    name = usernames[usernames.length - 1].getName();
            }

            names.add(name);
        }

        return names;
    }

    public static List<String> toDisplayNames(Collection<ProxiedPlayer> players) {
        List<String> displayNames = new ArrayList<>();

        for (ProxiedPlayer player : players)
            displayNames.add(User.getUser(player).getDisplayName());

        return displayNames;
    }

}
