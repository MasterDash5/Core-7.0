package xyz.dashnetwork.core.bungee.utils;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class SelectorUtils {

    private static BungeeCord bungee = BungeeCord.getInstance();

    public static Collection<ProxiedPlayer> getPlayers(CommandSender sender, String selector) {
        if (selector.equals("*"))
            return bungee.getPlayers();

        List<ProxiedPlayer> players = new ArrayList<>();

        for (String split : selector.split(",")) {
            ProxiedPlayer player = getPlayer(sender, split);

            if (player != null)
                players.add(player);
        }

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
