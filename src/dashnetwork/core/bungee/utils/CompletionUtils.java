package dashnetwork.core.bungee.utils;

import dashnetwork.core.utils.StringUtils;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

public class CompletionUtils {

    private static BungeeCord bungee = BungeeCord.getInstance();

    public static List<String> players(CommandSender sender, String argument) {
        List<String> players = new ArrayList<>();

        for (ProxiedPlayer online : bungee.getPlayers()) {
            String name = online.getName();

            if (VanishUtils.canSee(sender, online) && StringUtils.startsWithIgnoreCase(name, argument))
                players.add(name);
        }

        return players;
    }

    public static List<String> servers(CommandSender sender, String argument) {
        List<String> servers = new ArrayList<>();

        for (EnumServer server : EnumServer.values()) {
            String name = server.getName();

            if (server.getPermission().hasPermission(sender) && StringUtils.startsWithIgnoreCase(name, argument))
                servers.add(name);
        }

        return servers;
    }

}
