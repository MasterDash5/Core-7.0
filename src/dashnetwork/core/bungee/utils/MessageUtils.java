package dashnetwork.core.bungee.utils;

import dashnetwork.core.utils.ColorUtils;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class MessageUtils {

    private static BungeeCord bungee = BungeeCord.getInstance();

    public static void message(CommandSender sender, String message) {
        sender.sendMessage(TextComponent.fromLegacyText(message));
    }

    public static void message(CommandSender sender, BaseComponent... message) {
        sender.sendMessage(message);
    }

    public static void broadcast(PermissionType permission, String message) {
        for (User user : User.getUsers())
            if (permission.hasPermission(user))
                message(user, message);

        message(bungee.getConsole(), message);
    }

    public static void broadcast(PermissionType permission, BaseComponent... message) {
        for (User user : User.getUsers())
            if (permission.hasPermission(user))
                message(user, message);

        message(bungee.getConsole(), message);
    }

    public static void noPermissions(CommandSender sender) {
        message(sender, ColorUtils.translate("&6&l» &cYou don't have permission for that."));
    }

    public static void noPlayerFound(CommandSender sender) {
        message(sender, ColorUtils.translate("&6&l» &cNo player was found."));
    }

    public static void playersOnly() {
        message(bungee.getConsole(), ColorUtils.translate("&6&l» &cOnly players can do that."));
    }

}
