package dashnetwork.core.bungee.utils;

import dashnetwork.core.utils.StringUtils;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

public class CompletionUtils {

    private static BungeeCord bungee = BungeeCord.getInstance();

    public static List<String> players(String argument) {
        List<String> players = new ArrayList<>();

        for (ProxiedPlayer online : bungee.getPlayers()) {
            String name = online.getName();

            if (StringUtils.startsWithIgnoreCase(name, argument))
                players.add(name);
        }

        return players;
    }

}
