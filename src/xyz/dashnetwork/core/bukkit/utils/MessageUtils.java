package xyz.dashnetwork.core.bukkit.utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dashnetwork.core.bukkit.Core;
import xyz.dashnetwork.core.bukkit.pain.Pain;
import xyz.dashnetwork.core.utils.Channel;
import xyz.dashnetwork.core.utils.ColorUtils;
import xyz.dashnetwork.core.utils.MessageBuilder;

import java.io.DataOutputStream;
import java.io.IOException;

public class MessageUtils {

    private static Core plugin = Core.getInstance();
    private static CommandSender console = plugin.getServer().getConsoleSender();

    public static void message(CommandSender sender, String message) {
        sender.sendMessage(TextComponent.fromLegacyText(ColorUtils.translate(message)));
    }

    public static void message(CommandSender sender, BaseComponent... message) {
        sender.sendMessage(message);
    }

    public static void broadcast(Channel channel, String message) {
        switch (channel) {
            case LOCAL:
                for (User user : User.getUsers(true))
                    message(user, message);
                break;
            default:
                // TODO: Update Pain to use Channel instead of PermissionType
                byte permission = PermissionType.fromChannel(channel).toId();

                if (Core.isPainEnabled()) {
                    Pain pain = new Pain("broadcast");
                    DataOutputStream output = pain.getOutput();

                    try {
                        output.write(permission);
                        output.writeUTF(message);
                    } catch (IOException exception) {
                        message(console, "Failed to send global message");
                    }

                    pain.close();
                } else {
                    for (Player target : Bukkit.getOnlinePlayers()) {
                        ByteArrayDataOutput output = ByteStreams.newDataOutput();
                        output.write(permission);
                        output.writeUTF(message);

                        target.sendPluginMessage(Core.getInstance(), "dn:broadcast", output.toByteArray());
                        break;
                    }
                }
        }

        message(console, message);
    }

    @Deprecated
    public static void broadcast(boolean global, PermissionType permission, String message) {
        Channel channel;

        switch (permission) {
            case STAFF:
                channel = Channel.STAFF;
                break;
            case ADMIN:
                channel = Channel.ADMIN;
                break;
            case OWNER:
                channel = Channel.OWNER;
                break;
            default:
                channel = Channel.LOCAL;
        }

        if (global)
            channel = Channel.GLOBAL;

        broadcast(channel, message);
    }

    public static void broadcast(PermissionType permission, BaseComponent... message) {
        for (User user : User.getUsers(true))
            if (permission.hasPermission(user))
                message(user, message);

        message(console, message);
    }

    public static void noPermissions(CommandSender sender) {
        message(sender, "&6&l» &cYou don't have permission for that.");
    }

    public static void noPlayerFound(CommandSender sender) {
        message(sender, "&6&l» &cNo player was found.");
    }

    public static void playersOnly() {
        message(plugin.getServer().getConsoleSender(), "&6&l» &cOnly players can do that.");
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
