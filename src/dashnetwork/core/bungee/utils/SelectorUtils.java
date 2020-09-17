package dashnetwork.core.bungee.utils;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.*;

public class SelectorUtils {

    private static BungeeCord bungee = BungeeCord.getInstance();

    public static Collection<ProxiedPlayer> getPlayers(CommandSender sender, String selector) {
        if (selector.equals("*"))
            return bungee.getPlayers();

        List<ProxiedPlayer> players = new ArrayList<>();

        for (String split : selector.split(","))
            players.add(getPlayer(sender, split));

        return players;
    }

    public static ProxiedPlayer getPlayer(CommandSender sender, String selector) {
        if (selector.equals("@") && sender instanceof ProxiedPlayer)
            return (ProxiedPlayer) sender;

        try {
            UUID uuid = UUID.fromString(selector);

            return bungee.getPlayer(uuid);
        } catch (IllegalArgumentException exception) {
            return bungee.getPlayer(selector);
        }
    }

}
