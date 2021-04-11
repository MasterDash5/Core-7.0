package xyz.dashnetwork.core.bungee.utils;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.dashnetwork.core.utils.Channel;

public enum PermissionType {

    NONE((byte) 0),
    STAFF((byte) 1),
    ADMIN((byte) 2),
    OWNER((byte) 3);

    private byte id;

    PermissionType(byte id) {
        this.id = id;
    }

    public static PermissionType fromId(byte data) {
        return values()[data];
    }

    public static PermissionType fromChannel(Channel channel) {
        switch (channel) {
            case STAFF:
                return STAFF;
            case ADMIN:
                return ADMIN;
            case OWNER:
                return OWNER;
            default:
                return NONE;
        }
    }

    public byte toId() {
        return id;
    }

    public boolean hasPermission(CommandSender sender) {
        if (sender instanceof ProxiedPlayer)
            return hasPermission(User.getUser((ProxiedPlayer) sender));
        return true;
    }

    public boolean hasPermission(User user) {
        switch (this) {
            case STAFF:
                return user.isStaff();
            case ADMIN:
                return user.isAdmin();
            case OWNER:
                return user.isOwner();
            default:
                return true;
        }
    }

    public String toPermission() {
        if (this.equals(NONE))
            return null;

        return "dashnetwork." + toString().toLowerCase();
    }

}
