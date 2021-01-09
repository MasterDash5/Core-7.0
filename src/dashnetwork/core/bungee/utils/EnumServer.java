package dashnetwork.core.bungee.utils;

import dashnetwork.core.utils.GrammarUtils;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collection;
import java.util.Collections;

public enum EnumServer {

    LOBBY,
    PVP,
    CREATIVE,
    SURVIVAL,
    SKYBLOCK,
    STAFF,
    RIP,
    NOTHING;

    private ServerInfo info = BungeeCord.getInstance().getServerInfo(name());

    public PermissionType getPermission() {
        switch (this) {
            case LOBBY:
            case PVP:
            case CREATIVE:
            case SURVIVAL:
                return PermissionType.NONE;
            case STAFF:
                return PermissionType.STAFF;
            case SKYBLOCK:
            case RIP:
                return PermissionType.ADMIN;
        }

        return PermissionType.OWNER;
    }

    public String getName() {
        switch (this) {
            case PVP:
                return "PvP";
            case RIP:
                return "RIP";
        }

        return GrammarUtils.capitalization(name());
    }

    public String getVersion() {
        switch (this) {
            case PVP:
                return "1.8";
            case LOBBY:
            case RIP:
            case SKYBLOCK:
                return "1.12";
            case SURVIVAL:
            case CREATIVE:
            case STAFF:
                return "1.16";
        }

        return "Unknown";
    }

    public ServerInfo getServerInfo() {
        return info;
    }

    public boolean isDisabled() {
        return this.equals(SKYBLOCK);
    }

    public Collection<ProxiedPlayer> getPlayers() {
        if (info == null)
            return Collections.EMPTY_LIST;
        return info.getPlayers();
    }

    public void send(ProxiedPlayer player) {
        if (info != null)
            player.connect(info);
    }

}