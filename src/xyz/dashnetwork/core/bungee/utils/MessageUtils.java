package xyz.dashnetwork.core.bungee.utils;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import xyz.dashnetwork.core.utils.ColorUtils;

public class MessageUtils {

    private static CommandSender console = BungeeCord.getInstance().getConsole();

    public static void message(CommandSender sender, String message) {
        message(sender, TextComponent.fromLegacyText(ColorUtils.translate(message)));
    }

    public static void message(CommandSender sender, BaseComponent... message) {
        if (sender.equals(console))
            for (BaseComponent component : message)
                component.setBold(false); // Bold is ugly in Console

        sender.sendMessage(message);
    }

    public static void broadcast(PermissionType permission, String message) {
        for (User user : User.getUsers(true))
            if (permission.hasPermission(user))
                message(user, message);

        message(console, message);
    }

    public static void broadcast(PermissionType permission, BaseComponent... message) {
        for (User user : User.getUsers(true))
            if (permission.hasPermission(user))
                message(user, message);

        message(console, message);
    }

}
