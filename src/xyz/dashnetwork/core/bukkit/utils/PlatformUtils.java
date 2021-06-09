package xyz.dashnetwork.core.bukkit.utils;

import org.bukkit.Bukkit;
import xyz.dashnetwork.core.utils.ProtocolVersion;

public class PlatformUtils {

    private static ProtocolVersion serverVersion;

    public static String getPackageVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    public static ProtocolVersion getServerVersion() {
        if (serverVersion != null)
            return serverVersion;

        try {
            Class<?> craftServer = Class.forName("org.bukkit.craftbukkit." + getPackageVersion() + ".CraftServer");
            Object instance = craftServer.cast(Bukkit.getServer());
            Object server = craftServer.getMethod("getServer").invoke(instance);
            String version = (String) server.getClass().getMethod("getVersion").invoke(server);

            return (serverVersion = ProtocolVersion.fromString(version));
        } catch (Exception exception) {
            return (serverVersion = ProtocolVersion.UNKNOWN);
        }
    }

}