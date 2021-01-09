package dashnetwork.core.bungee.utils;

import static dashnetwork.core.bungee.utils.MessageUtils.*;
import dashnetwork.core.utils.*;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Messages {

    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("MMM d, hh:mma z");
    private static BungeeCord bungee = BungeeCord.getInstance();

    public static void forcedToServer(CommandSender sender, String username, String displayname, EnumServer server) {
        int players = server.getPlayers(!PermissionType.STAFF.hasPermission(sender)).size();
        String serverName = server.getName();
        MessageBuilder message = new MessageBuilder();

        message.append("&6&l» ");
        message.append("&6" + displayname).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + username);
        message.append("&7 sent you to ");
        message.append("&6" + serverName).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6&l" + serverName
                + "\n&7Version: " + server.getVersion()
                + "\n&6" + players + " &7Players");

        message(sender, message.build());
    }

    public static void joinServer(String username, String displayname) {
        MessageBuilder message = new MessageBuilder();
        message.append("&a&l» ");
        message.append("&6" + displayname).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + username);
        message.append("&a joined the server.");

        broadcast(PermissionType.NONE, message.build());
    }

    public static void leaveServer(String username, String displayname) {
        MessageBuilder message = new MessageBuilder();
        message.append("&c&l» ");
        message.append("&6" + displayname).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + username);
        message.append("&c left the server.");

        broadcast(PermissionType.NONE, message.build());
    }

    public static void leaveServerVanished(String username, String displayname) {
        MessageBuilder message = new MessageBuilder();
        message.append("&3&l» ");
        message.append("&6" + displayname).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + username);
        message.append("&3 left the server.");

        broadcast(PermissionType.STAFF, message.build());
    }

    public static void muted(CommandSender sender, PunishData punish) {
        Long expire = punish.getExpire();
        String date = expire == null ? "never" : timeFormat.format(new Date(expire));

        MessageBuilder reponse = new MessageBuilder();
        reponse.append("&6&l» &7You are muted. &6Hover for details")
                .hoverEvent(HoverEvent.Action.SHOW_TEXT,
                        "&6Muted by &7" + punish.getBanner()
                                + "\n&6Expires &7" + date
                                + "\n&6For &7" + punish.getReason());

        MessageUtils.message(sender, reponse.build());
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

    public static void exception(CommandSender sender, Exception exception) {
        String stacktrace = "&6" + exception.getClass().getName();

        for (StackTraceElement element : exception.getStackTrace())
            stacktrace += "\n&6at &7" + element.getClassName() + ": &6" + String.valueOf(element.getLineNumber()).replace("-1", "Unknown source");

        MessageBuilder message = new MessageBuilder();
        message.append("&6&l» &7An error occurred... hover for more info").hoverEvent(HoverEvent.Action.SHOW_TEXT, stacktrace);

        message(sender, message.build());
    }

    public static void sentToServer(CommandSender sender, EnumServer server) {
        int players = server.getPlayers(!PermissionType.STAFF.hasPermission(sender)).size();
        String serverName = server.getName();
        MessageBuilder message = new MessageBuilder();

        message.append("&6&l» &7Sending you to ");
        message.append("&6" + serverName).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6&l" + serverName
                + "\n&7Version: " + server.getVersion()
                + "\n&6" + players + " &7Players");

        message(sender, message.build());
    }

    public static void targetNoLongerIn(CommandSender sender, List<ProxiedPlayer> targets, String channel) {
        String displaynames = StringUtils.fromList(NameUtils.toDisplayNames(targets), false, false);
        String names = StringUtils.fromList(NameUtils.toNames(targets), false, false);

        MessageBuilder message = new MessageBuilder();
        message.append("&6&l» ");
        message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
        message.append("&7 " + (targets.size() > 1 ? "are" : "is") + " no longer in &6" + channel);

        MessageUtils.message(sender, message.build());
    }

    public static void targetNowIn(CommandSender sender, List<ProxiedPlayer> targets, String channel) {
        String displaynames = StringUtils.fromList(NameUtils.toDisplayNames(targets), false, false);
        String names = StringUtils.fromList(NameUtils.toNames(targets), false, false);

        MessageBuilder message = new MessageBuilder();
        message.append("&6&l» ");
        message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
        message.append("&7 " + (targets.size() > 1 ? "are" : "is") + " now in &6" + channel);

        message(sender, message.build());
    }

    public static void targetSentToServer(CommandSender sender, List<ProxiedPlayer> targets, EnumServer server) {
        int players = server.getPlayers(!PermissionType.STAFF.hasPermission(sender)).size();
        String serverName = server.getName();
        String displaynames = StringUtils.fromList(NameUtils.toDisplayNames(targets), false, false);
        String names = StringUtils.fromList(NameUtils.toNames(targets), false, false);

        MessageBuilder message = new MessageBuilder();
        message.append("&6&l» ");
        message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
        message.append("&7 " + (targets.size() > 1 ? "were" : "was") + " moved to ");
        message.append("&6" + serverName).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6&l" + serverName
                + "\n&7Version: " + server.getVersion()
                + "\n&6" + players + " &7Players");

        message(sender, message.build());
    }

}
