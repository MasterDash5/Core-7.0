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
import java.util.UUID;

public class PunishUtils {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("MMM d, hh:mma z");
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

    public static void mute(UUID uuid, String name, Long expire, CommandSender punisher, String reason) {
        String username = NameUtils.getName(punisher);
        String displayname = NameUtils.getDisplayName(punisher);
        String date = expire == null ? "never" : formatter.format(new Date(expire));
        PunishData punish = new PunishData(expire, username, reason);

        mutes.put(uuid.toString(), punish);

        MessageBuilder broadcast = new MessageBuilder();
        broadcast.append("&6&l» ");
        broadcast.append("&6" + displayname + "&7 muted &6" + name)
                .hoverEvent(HoverEvent.Action.SHOW_TEXT,
                        "&6" + uuid.toString()
                                + "\n&6Muted by &7" + username
                                + "\n&6Expires &7" + date
                                + "\n&6For &7" + reason);

        MessageUtils.broadcast(PermissionType.NONE, broadcast.build());

        ProxiedPlayer target = bungee.getPlayer(uuid);

        if (target != null)
            Messages.muted(target, punish);
    }

    public static void ban(UUID uuid, String name, Long expire, CommandSender punisher, String reason) {
        String username = NameUtils.getName(punisher);
        String displayname = NameUtils.getDisplayName(punisher);
        String date = expire == null ? "never" : formatter.format(new Date(expire));

        bans.put(uuid.toString(), new PunishData(expire, name, reason));

        ProxiedPlayer target = bungee.getPlayer(uuid);

        if (target != null && !User.getUser(target).isOwner()) {
            MessageBuilder message = new MessageBuilder();
            message.append("&7You have been banned from &6&lDashNetwork\n");
            message.append("\n&7Banned by &6" + username);
            message.append("\n&7Expires &6" + date);
            message.append("\n&7For &6" + reason);

            target.disconnect(message.build());
        }

        MessageBuilder broadcast = new MessageBuilder();
        broadcast.append("&6&l» ");
        broadcast.append("&6" + displayname + "&7 banned &6" + name)
                .hoverEvent(HoverEvent.Action.SHOW_TEXT,
                        "&6" + uuid.toString()
                                + "\n&6Banned by &7" + username
                                + "\n&6Expires &7" + date
                                + "\n&6For &7" + reason);

        MessageUtils.broadcast(PermissionType.NONE, broadcast.build());
    }

    public static void ipban(String address, Long expire, CommandSender punisher, String reason) {
        String name = NameUtils.getName(punisher);
        String displayname = NameUtils.getDisplayName(punisher);
        String date = expire == null ? "never" : formatter.format(new Date(expire));

        ipbans.put(address, new PunishData(expire, displayname, reason));

        MessageBuilder broadcast = new MessageBuilder();
        broadcast.append("&6&l» ");
        broadcast.append("&6" + displayname + "&7 banned &6" + address)
                .hoverEvent(HoverEvent.Action.SHOW_TEXT,
                        "&6Banned by &7" + name
                                + "\n&6Expires &7" + date
                                + "\n&6For &7" + reason);

        MessageUtils.broadcast(PermissionType.ADMIN, broadcast.build());

        for (User user : User.getUsers(true)) {
            ProxiedPlayer player = user.getPlayer();

            if (address.equals(((InetSocketAddress) player.getSocketAddress()).getAddress().getHostAddress()))
                ban(player.getUniqueId(), player.getName(), expire, punisher, reason);
        }
    }

    public static SimpleDateFormat getDateFormat() {
        return formatter;
    }

}
