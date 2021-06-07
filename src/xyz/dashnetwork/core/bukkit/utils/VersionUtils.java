package xyz.dashnetwork.core.bukkit.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import us.myles.ViaVersion.api.Via;
import xyz.dashnetwork.core.utils.ProtocolVersion;

public class VersionUtils {

    public static ProtocolVersion getPlayerVersion(Player player) {
        if (Bukkit.getPluginManager().isPluginEnabled("ViaVersion"))
            return ProtocolVersion.fromId(Via.getAPI().getPlayerVersion(player));
        return ProtocolVersion.UNKNOWN;
    }

}
