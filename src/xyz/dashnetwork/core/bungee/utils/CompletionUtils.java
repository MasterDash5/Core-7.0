package xyz.dashnetwork.core.bungee.utils;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.dashnetwork.core.utils.StringUtils;

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

    public static List<String> offline(String argument) {
        List<String> players = new ArrayList<>();

        for (String name : DataUtils.getNames().values())
            if (StringUtils.startsWithIgnoreCase(name, argument))
                players.add(name);

        return players;
    }

    public static List<String> servers(CommandSender sender, String argument) {
        List<String> servers = new ArrayList<>();

        for (Server server : ServerList.getServers()) {
            String name = server.getName();

            if (server.getPermission().hasPermission(sender) && StringUtils.startsWithIgnoreCase(name, argument))
                servers.add(name);
        }

        return servers;
    }

    public static List<String> fromEnum(String argument, Enum[] array) {
        List<String> list = new ArrayList<>();

        for (Enum entry : array) {
            String name = entry.name().toLowerCase();

            if (StringUtils.startsWithIgnoreCase(name, argument))
                list.add(name);
        }

        return list;
    }

}
