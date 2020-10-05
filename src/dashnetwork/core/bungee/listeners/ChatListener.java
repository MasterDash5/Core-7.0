package dashnetwork.core.bungee.listeners;

import dashnetwork.core.bungee.events.UserChatEvent;
import dashnetwork.core.bungee.utils.*;
import dashnetwork.core.utils.*;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.event.EventHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatListener implements Listener {

    private BungeeCord bungee = BungeeCord.getInstance();
    private PluginManager pluginManager = bungee.getPluginManager();
    private LuckPerms lp = LuckPermsProvider.get();

    @EventHandler
    public void onChat(ChatEvent event) {
        Connection connection = event.getSender();

        if (connection instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) connection;
            User user = User.getUser(player);
            String message = event.getMessage();

            user.updateDisplayName(true);

            if (event.isProxyCommand() || event.isCommand()) {
                MessageBuilder broadcast = new MessageBuilder();
                broadcast.append("&c&lCS ");
                broadcast.append("&6" + user.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + player.getName());
                broadcast.append(" &e&l> &b" + message);

                for (User online : User.getUsers(true))
                    if (online.inCommandSpy())
                        online.sendMessage(broadcast.build());
            } else {
                event.setCancelled(true);

                if (user.isMuted()) {
                    PunishData data = DataUtils.getMutes().get(player.getUniqueId().toString());
                    Long expire = data.getExpire();
                    String date = expire == null ? "never" : new SimpleDateFormat("MMM d, hh:mm a z").format(new Date(expire));

                    MessageBuilder reponse = new MessageBuilder();
                    reponse.append("&6&l» &7You are muted. Hover for details")
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT,
                                    "&6Muted by &7" + data.getBanner()
                                            + "\n&6Expires &7" + date
                                            + "\n&6Reason: &7" + data.getReason());

                    MessageUtils.message(user, reponse.build());

                    return;
                }

                if (!user.isStaff())
                    message = ColorUtils.filter(message, true, true, true, false, false, false);

                String trimmed = message.length() > 3 ? message.substring((message.substring(3).startsWith(" ") ? 4 : 3)) : "";

                if (user.inLocalChat())
                    localChat(event, message);
                else if (user.isOwner() && StringUtils.startsWithIgnoreCase(message, "@lc"))
                    localChat(event, trimmed);
                else if (user.inOwnerChat())
                    ownerChat(user, message);
                else if (user.isOwner() && LazyUtils.anyStartsWithIgnoreCase(message, "@oc", "@dc"))
                    ownerChat(user, trimmed);
                else if (user.inAdminChat())
                    adminChat(user, message);
                else if (user.isAdmin() && StringUtils.startsWithIgnoreCase(message, "@ac"))
                    adminChat(user, trimmed);
                else if (user.inStaffChat())
                    staffChat(user, message);
                else if (user.isStaff() && StringUtils.startsWithIgnoreCase(message, "@sc"))
                    staffChat(user, trimmed);
                else
                    globalChat(user, message);
            }
        }
    }

    private void localChat(ChatEvent event, String message) {
        event.setCancelled(false);
        event.setMessage(message);
    }

    private void ownerChat(User user, String input) {
        MessageBuilder broadcast = new MessageBuilder();
        broadcast.append("&9&lOwner ");
        broadcast.append("&6" + user.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + user.getPlayer().getName());
        broadcast.append(" &6&l> &c" + input);

        MessageUtils.broadcast(PermissionType.OWNER, broadcast.build());

        pluginManager.callEvent(new UserChatEvent(user, PermissionType.OWNER, input));
    }

    private void adminChat(User user, String input) {
        MessageBuilder broadcast = new MessageBuilder();
        broadcast.append("&9&lAdmin ");
        broadcast.append("&6" + user.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + user.getPlayer().getName());
        broadcast.append(" &6&l> &3" + input);

        MessageUtils.broadcast(PermissionType.ADMIN, broadcast.build());

        pluginManager.callEvent(new UserChatEvent(user, PermissionType.ADMIN, input));
    }

    private void staffChat(User user, String input) {
        MessageBuilder broadcast = new MessageBuilder();
        broadcast.append("&9&lStaff ");
        broadcast.append("&6" + user.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + user.getPlayer().getName());
        broadcast.append(" &6&l> &6" + input);

        MessageUtils.broadcast(PermissionType.STAFF, broadcast.build());

        pluginManager.callEvent(new UserChatEvent(user, PermissionType.STAFF, input));
    }

    private void globalChat(User user, String input) {
        MessageBuilder broadcast = new MessageBuilder();
        broadcast.append("&6" + user.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + user.getPlayer().getName());
        broadcast.append(" &e&l> &f" + input);

        MessageUtils.broadcast(PermissionType.NONE, broadcast.build());

        pluginManager.callEvent(new UserChatEvent(user, PermissionType.NONE, input));
    }

}
