package xyz.dashnetwork.core.bukkit.utils;

import xyz.dashnetwork.core.utils.ProtocolVersion;

public class PlatformUtils {

    public static ProtocolVersion getServerVersion() {
        return ProtocolVersion.fromString(ReflectionUtils.getServerVersion());
    }

}
