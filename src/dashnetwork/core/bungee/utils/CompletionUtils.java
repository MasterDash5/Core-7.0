package dashnetwork.core.bungee.utils;

import dashnetwork.core.utils.StringUtils;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.minecraft.server.v1_8_R3.EnumDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

        for (ServerInfo server : ServerUtils.getServers()) {
            String name = server.getMotd();

            if (ServerUtils.hasPermission(sender, server) && StringUtils.startsWithIgnoreCase(name, argument))
                servers.add(name);
        }

        return servers;
    }

    public static List<String> fromEnum(CommandSender sender, String argument, Enum[] array) {
        List<String> list = new ArrayList<>();

        for (Enum entry : array) {
            String name = entry.name().toLowerCase();

            if (StringUtils.startsWithIgnoreCase(name, argument))
                list.add(name);
        }

        return list;
    }

}
