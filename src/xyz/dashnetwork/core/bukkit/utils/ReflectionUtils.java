package xyz.dashnetwork.core.bukkit.utils;

import org.bukkit.Bukkit;
import xyz.dashnetwork.core.bukkit.Core;

public class ReflectionUtils {

    private static String packageVersion = Core.getInstance().getServer().getClass().getPackage().getName().split("\\.")[3];

    public static String getPackageVersion() {
        return packageVersion;
    }

    public static String getServerVersion() {
        try {
            Class craftServer = Class.forName("org.bukkit.craftbukkit." + packageVersion + ".CraftServer");
            Object instance = craftServer.cast(Bukkit.getServer());
            Object server = craftServer.getMethod("getServer").invoke(instance);
            String version = (String) server.getClass().getMethod("getVersion").invoke(server);

            return version;
        } catch (Exception exception) {}
        return "Unknown";
    }

}
