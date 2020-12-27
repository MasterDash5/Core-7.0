package dashnetwork.core.bungee.utils;

import dashnetwork.core.utils.ColorUtils;
import dashnetwork.core.utils.MessageBuilder;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class MessageUtils {

    private static BungeeCord bungee = BungeeCord.getInstance();

    public static void message(CommandSender sender, String message) {
        sender.sendMessage(TextComponent.fromLegacyText(ColorUtils.translate(message)));
    }

    public static void message(CommandSender sender, BaseComponent... message) {
        sender.sendMessage(message);
    }

    public static void broadcast(PermissionType permission, String message) {
        for (User user : User.getUsers(true))
            if (permission.hasPermission(user))
                message(user, message);

        message(bungee.getConsole(), message);
    }

    public static void broadcast(PermissionType permission, BaseComponent... message) {
        for (User user : User.getUsers(true))
            if (permission.hasPermission(user))
                message(user, message);

        message(bungee.getConsole(), message);
    }

    public static void noPermissions(CommandSender sender) {
        message(sender, "&6&l» &cYou don't have permission for that.");
    }

    public static void noPlayerFound(CommandSender sender) {
        message(sender, "&6&l» &cNo player was found.");
    }

    public static void playersOnly() {
        message(bungee.getConsole(), "&6&l» &cOnly players can do that.");
    }

    public static void sendException(CommandSender sender, Exception exception) {
        String stacktrace = "&6" + exception.getClass().getName();

        for (StackTraceElement element : exception.getStackTrace())
            stacktrace += "\n&6at &7" + element.getClassName() + ": &6" + String.valueOf(element.getLineNumber()).replace("-1", "Unknown source");

        MessageBuilder message = new MessageBuilder();
        message.append("&6&l» &7An error occurred... hover for more info").hoverEvent(HoverEvent.Action.SHOW_TEXT, stacktrace);

        message(sender, message.build());
    }

}
