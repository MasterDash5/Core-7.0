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

    private static BungeeCord bungee = BungeeCord.getInstance();
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, hh:mma z");
    private static Map<String, PunishData> mutes = DataUtils.getMutes();
    private static Map<String, PunishData> bans = DataUtils.getBans();
    private static Map<String, PunishData> ipbans = DataUtils.getIpbans();

    public static void kick(ProxiedPlayer player, CommandSender punisher, String reason) {
        String name = NameUtils.getName(punisher);
        String displayname = NameUtils.getDisplayName(punisher);

        MessageBuilder message = new MessageBuilder();
        message.append("&7You have been kicked from &6&lDashNetwork\n\n");
        message.append("&7Kicked by &6" + name + "\n");
        message.append("&7For &6" + reason);

        player.disconnect(message.build());

        MessageBuilder broadcast = new MessageBuilder();
        broadcast.append("&6&l» &6" + displayname + "&7 kicked &6" + player.getDisplayName())
                .hoverEvent(HoverEvent.Action.SHOW_TEXT,
                        "&6Kicked by &7" + name
                        + "\n&6Reason: &c" + reason);

        MessageUtils.broadcast(PermissionType.STAFF, broadcast.build());
    }

    public static void mute(ProxiedPlayer player, Long expire, CommandSender punisher, String reason) {
        String name = NameUtils.getName(punisher);
        String displayname = NameUtils.getDisplayName(punisher);
        String date = expire == null ? "never" : dateFormat.format(new Date(expire));

        mutes.put(player.getUniqueId().toString(), new PunishData(expire, toUuid(punisher), reason));

        MessageBuilder message = new MessageBuilder();
        message.append("&6&l» ");
        message.append("&6" + displayname + "&7 muted you. &6Hover for details")
                .hoverEvent(HoverEvent.Action.SHOW_TEXT,
                        "&6Muted by &7" + name
                                + "\n&6Expires &7" + date
                                + "\n&6Reason: &7" + reason);

        MessageBuilder broadcast = new MessageBuilder();
        broadcast.append("&6&l» &6" + displayname + "&7 muted &6" + player.getDisplayName())
                .hoverEvent(HoverEvent.Action.SHOW_TEXT,
                        "&6Muted by &7" + name
                        + "\n&6Expires &7" + date
                        + "\n&6Reason: &7" + reason);

        MessageUtils.broadcast(PermissionType.STAFF, broadcast.build());
    }

    public static void ban(ProxiedPlayer player, Long expire, CommandSender punisher, String reason) {
        String name = NameUtils.getName(punisher);
        String displayname = NameUtils.getDisplayName(punisher);
        String date = expire == null ? "never" : dateFormat.format(new Date(expire));

        bans.put(player.getUniqueId().toString(), new PunishData(expire, toUuid(punisher), reason));

        MessageBuilder message = new MessageBuilder();
        message.append("&7You have been banned from &6&lDashNetwork\n\n");
        message.append("&7Banned by &6" + name + "\n");
        message.append("&7For &6" + reason);

        player.disconnect(message.build());

        MessageBuilder broadcast = new MessageBuilder();
        broadcast.append("&6&l» &6" + displayname + "&7 banned &6" + player.getDisplayName())
                .hoverEvent(HoverEvent.Action.SHOW_TEXT,
                        "&6Banned by &7" + name
                                + "\n&6Expires &7" + date
                                + "\n&6Reason: &7" + reason);

        MessageUtils.broadcast(PermissionType.STAFF, broadcast.build());
    }

    public static void ipban(String address, Long expire, CommandSender punisher, String reason) {
        String name = NameUtils.getName(punisher);
        String displayname = NameUtils.getDisplayName(punisher);
        String date = expire == null ? "never" : dateFormat.format(new Date(expire));

        ipbans.put(address, new PunishData(expire, toUuid(punisher), reason));

        MessageBuilder broadcast = new MessageBuilder();
        broadcast.append("&6&l» &6" + displayname + "&7 banned &6" + address)
                .hoverEvent(HoverEvent.Action.SHOW_TEXT,
                        "&6Banned by &7" + name
                                + "\n&6Expires &7" + date
                                + "\n&6Reason: &7" + reason);

        MessageUtils.broadcast(PermissionType.STAFF, broadcast.build());

        for (ProxiedPlayer player : bungee.getPlayers())
            if (address.equals(((InetSocketAddress) player.getSocketAddress()).getAddress().getHostAddress()))
                ban(player, expire, punisher, reason);
    }

    private static String toUuid(CommandSender sender) {
        if (sender instanceof ProxiedPlayer)
            return ((ProxiedPlayer) sender).getUniqueId().toString();
        return "Console";
    }

}
