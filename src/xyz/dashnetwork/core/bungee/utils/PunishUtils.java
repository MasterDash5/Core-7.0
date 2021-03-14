package xyz.dashnetwork.core.bungee.utils;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.dashnetwork.core.utils.MessageBuilder;
import xyz.dashnetwork.core.utils.PunishData;
import xyz.dashnetwork.core.utils.TimeUtils;

import java.util.Date;
import java.util.Map;

public class PunishUtils {

    private static BungeeCord bungee = BungeeCord.getInstance();
    private static Map<String, PunishData> mutes = DataUtils.getMutes();
    private static Map<String, PunishData> bans = DataUtils.getBans();
    private static Map<String, PunishData> ipbans = DataUtils.getIpbans();

    public static void kick(User user, CommandSender punisher, String reason) {
        ProxiedPlayer player = user.getPlayer();
        String name = NameUtils.getName(punisher);
        String displayname = NameUtils.getDisplayName(punisher);

        if (!user.isOwner()) {
            MessageBuilder message = new MessageBuilder();
            message.append("&7You have been kicked from &6&lDashNetwork\n");
            message.append("\n&7Kicked by &6" + name);
            message.append("\n&7For &6" + reason);

            player.disconnect(message.build());
        }

        MessageBuilder broadcast = new MessageBuilder();
        broadcast.append("&6&l» &6" + displayname + "&7 kicked &6" + user.getName())
                .hoverEvent(HoverEvent.Action.SHOW_TEXT,
                        "&6" + player.getUniqueId().toString()
                                + "\n&6Kicked by &7" + name
                                + "\n&6For &c" + reason);

        MessageUtils.broadcast(PermissionType.NONE, broadcast.build());
    }

    public static void mute(OfflineUser offline, CommandSender punisher, Long expire, String reason) {
        String uuid = offline.getUniqueId().toString();
        String name = offline.getName();
        String username = NameUtils.getName(punisher);
        String displayname = NameUtils.getDisplayName(punisher);
        String date = expire == null ? "never" : TimeUtils.TIME_FORMAT.format(new Date(expire));
        PunishData punish = new PunishData(expire, username, reason);

        mutes.put(uuid, punish);

        MessageBuilder broadcast = new MessageBuilder();
        broadcast.append("&6&l» ");
        broadcast.append("&6" + displayname + "&7 muted &6" + name)
                .hoverEvent(HoverEvent.Action.SHOW_TEXT,
                        "&6" + uuid
                                + "\n&6Muted by &7" + username
                                + "\n&6Expires &7" + date
                                + "\n&6For &7" + reason);

        MessageUtils.broadcast(PermissionType.NONE, broadcast.build());

        if (offline.isOnline())
            Messages.muted((User) offline, punish);
    }

    public static void ban(OfflineUser offline, CommandSender punisher, Long expire, String reason) {
        String uuid = offline.getUniqueId().toString();
        String name = offline.getName();
        String username = NameUtils.getName(punisher);
        String displayname = NameUtils.getDisplayName(punisher);
        String date = expire == null ? "never" : TimeUtils.TIME_FORMAT.format(new Date(expire));

        bans.put(uuid, new PunishData(expire, username, reason));

        if (offline.isOnline()) {
            User user = (User) offline;

            if (!user.isOwner()) {
                MessageBuilder message = new MessageBuilder();
                message.append("&7You have been banned from &6&lDashNetwork\n");
                message.append("\n&7Banned by &6" + username);
                message.append("\n&7Expires &6" + date);
                message.append("\n&7For &6" + reason);

                user.getPlayer().disconnect(message.build());
            }
        }

        MessageBuilder broadcast = new MessageBuilder();
        broadcast.append("&6&l» ");
        broadcast.append("&6" + displayname + "&7 banned &6" + name)
                .hoverEvent(HoverEvent.Action.SHOW_TEXT,
                        "&6" + uuid
                                + "\n&6Banned by &7" + username
                                + "\n&6Expires &7" + date
                                + "\n&6For &7" + reason);

        MessageUtils.broadcast(PermissionType.NONE, broadcast.build());
    }

    public static void ipban(String address, Long expire, CommandSender punisher, String reason) {
        String name = NameUtils.getName(punisher);
        String displayname = NameUtils.getDisplayName(punisher);
        String date = expire == null ? "never" : TimeUtils.TIME_FORMAT.format(new Date(expire));

        ipbans.put(address, new PunishData(expire, displayname, reason));

        MessageBuilder broadcast = new MessageBuilder();
        broadcast.append("&6&l» ");
        broadcast.append("&6" + displayname + "&7 banned &6" + address)
                .hoverEvent(HoverEvent.Action.SHOW_TEXT,
                        "&6Banned by &7" + name
                                + "\n&6Expires &7" + date
                                + "\n&6For &7" + reason);

        MessageUtils.broadcast(PermissionType.ADMIN, broadcast.build());

        for (User user : User.getUsers(true))
            if (address.equals(user.getAddress()))
                ban(OfflineUser.getOfflineUser(user.getUniqueId(), user.getName()), punisher, expire, reason);
    }

}
