package xyz.dashnetwork.core.bukkit.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

    public byte toId() {
        return id;
    }

    public boolean hasPermission(CommandSender sender) {
        if (sender instanceof Player)
            return hasPermission(User.getUser((Player) sender));
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
