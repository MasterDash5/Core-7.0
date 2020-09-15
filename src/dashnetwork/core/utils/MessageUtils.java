package dashnetwork.core.utils;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class MessageUtils {

    private static BungeeCord bungee = BungeeCord.getInstance();

    public static void message(CommandSender sender, String message) {
        sender.sendMessage(TextComponent.fromLegacyText(message));
    }

    public static void broadcast(PermissionType permission, String message) {
        for (User user : User.getUsers())
            if (permission.hasPermission(user))
                message(user, message);

        message(bungee.getConsole(), message);
    }

    public static void noPermissions(CommandSender sender) {
        message(sender, ColorUtils.translate("&6&l» &7You don't have permission for that"));
    }

}
