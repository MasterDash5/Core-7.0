package dashnetwork.core.bungee.utils;

import dashnetwork.core.utils.MapUtils;
import dashnetwork.core.utils.PlayerProfile;
import dashnetwork.core.utils.Username;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class NameUtils {

    private static CommandSender console = BungeeCord.getInstance().getConsole();

    public static String getDisplayName(CommandSender sender) {
        if (sender instanceof ProxiedPlayer)
            return User.getUser((ProxiedPlayer) sender).getDisplayName();
        else if (sender.equals(console))
            return "Console";

        return sender.getName();
    }

    public static String getName(CommandSender sender) {
        if (sender.equals(console))
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

    public static String getUsername(UUID uuid) {
        String username = DataUtils.getNames().get(uuid.toString());

        if (username == null) {
            Username[] usernames = MojangUtils.getNameHistoryFromUuid(uuid);

            if (usernames == null)
                return null;

            username = usernames[usernames.length - 1].getName();
        }

        return username;
    }

    public static UUID getUUID(String username) {
        String uuid = MapUtils.getKeyFromValue(DataUtils.getNames(), username);

        if (uuid == null) {
            PlayerProfile profile = MojangUtils.getUuidFromName(username);

            if (profile == null)
                return null;

            uuid = profile.getUuid().toString();
        }

        return UUID.fromString(uuid);
    }


}
