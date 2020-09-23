package dashnetwork.core.bungee.utils;

import dashnetwork.core.utils.MessageBuilder;
import dashnetwork.core.utils.PunishData;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class PunishUtils {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, hh:mma z");
    private static BungeeCord bungee = BungeeCord.getInstance();
    private static Map<String, PunishData> mutes = DataUtils.getMutes();
    private static Map<String, PunishData> bans = DataUtils.getBans();
    private static Map<String, PunishData> ipbans = DataUtils.getIpbans();

    public static void kick(User user, CommandSender punisher, String reason) {
        ProxiedPlayer player = user.getPlayer();
        String name = NameUtils.getName(punisher);
        String displayname = NameUtils.getDisplayName(punisher);

        MessageBuilder message = new MessageBuilder();
        message.append("&7You have been kicked from &6&lDashNetwork\n\n");
        message.append("&7Kicked by &6" + displayname);
        message.append("\n&7For &6" + reason);

        player.disconnect(message.build());

        MessageBuilder broadcast = new MessageBuilder();
        broadcast.append("&6&l» &6" + displayname + "&7 kicked &6" + user.getDisplayName())
                .hoverEvent(HoverEvent.Action.SHOW_TEXT,
                        "&6Kicked by &7" + name
                        + "\n&6Reason: &c" + reason);

        MessageUtils.broadcast(PermissionType.NONE, broadcast.build());
    }

    public static void mute(User user, Long expire, CommandSender punisher, String reason) {
        ProxiedPlayer player = user.getPlayer();
        String name = NameUtils.getName(punisher);
        String displayname = NameUtils.getDisplayName(punisher);
        String date = expire == null ? "never" : dateFormat.format(new Date(expire));

        mutes.put(player.getUniqueId().toString(), new PunishData(expire, displayname, reason));

        MessageBuilder message = new MessageBuilder();
        message.append("&6&l» ");
        message.append("&6" + displayname + "&7 muted you. &6Hover for details")
                .hoverEvent(HoverEvent.Action.SHOW_TEXT,
                        "&6Muted by &7" + name
                                + "\n&6Expires &7" + date
                                + "\n&6Reason: &7" + reason);

        MessageUtils.message(user, message.build());

        MessageBuilder broadcast = new MessageBuilder();
        broadcast.append("&6&l» &6" + displayname + "&7 muted &6" + user.getDisplayName())
                .hoverEvent(HoverEvent.Action.SHOW_TEXT,
                        "&6Muted by &7" + name
                        + "\n&6Expires &7" + date
                        + "\n&6Reason: &7" + reason);

        MessageUtils.broadcast(PermissionType.NONE, broadcast.build());
    }

    public static void ban(User user, Long expire, CommandSender punisher, String reason) {
        ProxiedPlayer player = user.getPlayer();
        String name = NameUtils.getName(punisher);
        String displayname = NameUtils.getDisplayName(punisher);
        String date = expire == null ? "never" : dateFormat.format(new Date(expire));

        bans.put(player.getUniqueId().toString(), new PunishData(expire, displayname, reason));

        MessageBuilder message = new MessageBuilder();
        message.append("&7You have been banned from &6&lDashNetwork\n\n");
        message.append("&7Banned by &6" + displayname);
        message.append("\n&7Expires &6" + date);
        message.append("\n&7For &6" + reason);

        player.disconnect(message.build());

        MessageBuilder broadcast = new MessageBuilder();
        broadcast.append("&6&l» &6" + displayname + "&7 banned &6" + user.getDisplayName())
                .hoverEvent(HoverEvent.Action.SHOW_TEXT,
                        "&6Banned by &7" + name
                                + "\n&6Expires &7" + date
                                + "\n&6Reason: &7" + reason);

        MessageUtils.broadcast(PermissionType.NONE, broadcast.build());
    }

    public static void ipban(String address, Long expire, CommandSender punisher, String reason) {
        String name = NameUtils.getName(punisher);
        String displayname = NameUtils.getDisplayName(punisher);
        String date = expire == null ? "never" : dateFormat.format(new Date(expire));

        ipbans.put(address, new PunishData(expire, displayname, reason));

        MessageBuilder broadcast = new MessageBuilder();
        broadcast.append("&6&l» &6" + displayname + "&7 banned &6" + address)
                .hoverEvent(HoverEvent.Action.SHOW_TEXT,
                        "&6Banned by &7" + name
                                + "\n&6Expires &7" + date
                                + "\n&6Reason: &7" + reason);

        MessageUtils.broadcast(PermissionType.ADMIN, broadcast.build());

        for (User user : User.getUsers(true))
            if (address.equals(((InetSocketAddress) user.getPlayer().getSocketAddress()).getAddress().getHostAddress()))
                ban(user, expire, punisher, reason);
    }

    public static SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

}
