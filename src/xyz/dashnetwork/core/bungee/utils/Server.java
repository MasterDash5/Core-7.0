package xyz.dashnetwork.core.bungee.utils;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

public class Server {

    private ServerInfo serverInfo;
    private String name;
    private String[] aliases;
    private int version;
    private PermissionType permission;
    private boolean bedrock;

    protected Server(String name, String[] aliases, int version, PermissionType permission, boolean bedrock) {
        this.serverInfo = BungeeCord.getInstance().getServerInfo(name);
        this.name = name;
        this.aliases = aliases;
        this.version = version;
        this.permission = permission;
        this.bedrock = bedrock;
    }

    public ServerInfo getServerInfo() {
        return serverInfo;
    }

    public String getName() {
        return name;
    }

    public String[] getAliases() {
        return aliases;
    }

    public int getVersion() {
        return version;
    }

    public PermissionType getPermission() {
        return permission;
    }

    public boolean isBedrock() {
        return bedrock;
    }

    public void send(ProxiedPlayer player) {
        player.connect(serverInfo);
    }

    public List<ProxiedPlayer> getPlayers(boolean showVanished) {
        List<ProxiedPlayer> players = new ArrayList<>();

        for (ProxiedPlayer player : serverInfo.getPlayers())
            if (showVanished || !User.getUser(player).isVanished())
                players.add(player);

        return players;
    }

}