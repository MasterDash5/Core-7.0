package dashnetwork.core.utils;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class SelectorUtils {

    private static BungeeCord bungee = BungeeCord.getInstance();

    public static Collection<ProxiedPlayer> getPlayers(String selector) {
        if (selector.equals("*"))
            return bungee.getPlayers();

        List<ProxiedPlayer> players = new ArrayList<>();

        for (String split : selector.split(",")) {
            try {
                UUID uuid = UUID.fromString(split);
                ProxiedPlayer player = bungee.getPlayer(uuid);

                if (player != null)
                    players.add(player);
            } catch (IllegalArgumentException exception) {
                ProxiedPlayer player = bungee.getPlayer(selector);

                if (player != null)
                    players.add(player);
            }
        }

        return players;
    }

}
