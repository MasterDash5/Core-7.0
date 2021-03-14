package xyz.dashnetwork.core.bungee.utils;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ServerUtils {

    private static BungeeCord bungee = BungeeCord.getInstance();

    public static Collection<ServerInfo> getServers() {
        return bungee.getServers().values();
    }

    public static ServerInfo getServer(String name) {
        return bungee.getServerInfo(name);
    }

    public static boolean hasPermission(CommandSender sender, ServerInfo server) {
        return !server.isRestricted() || PermissionType.ADMIN.hasPermission(sender);
    }

    public static List<ProxiedPlayer> getPlayers(ServerInfo server, boolean showVanished) {
        Collection<ProxiedPlayer> players = server.getPlayers();

        if (!showVanished)
            return new ArrayList<>(players);

        List<ProxiedPlayer> filtered = new ArrayList<>();

        for (ProxiedPlayer player : players) {
            User user = User.getUser(player);

            if (!user.isVanished())
                filtered.add(player);
        }

        return filtered;
    }

}
