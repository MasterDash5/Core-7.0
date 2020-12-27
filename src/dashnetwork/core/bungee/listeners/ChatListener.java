package dashnetwork.core.bungee.listeners;

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

            if (event.isCommand()) {
                MessageBuilder broadcast = new MessageBuilder();
                broadcast.append("&c&lCS ");
                broadcast.append("&6" + user.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + player.getName());
                broadcast.append(" &e&l>&b " + message);

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
                    reponse.append("&6&l» &7You are muted. &6Hover for details")
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT,
                                    "&6Muted by &7" + data.getBanner()
                                            + "\n&6Expires &7" + date
                                            + "\n&6Reason: &7" + data.getReason());

                    MessageUtils.message(user, reponse.build());

                    return;
                }

                if (!user.isStaff())
                    message = ColorUtils.filter(message, true, true, true, true, false, false);

                String trimmed = message.length() > 3 ? message.substring((message.substring(3).startsWith(" ") ? 4 : 3)) : "";
                boolean owner = user.isOwner();
                boolean admin = user.isAdmin();
                boolean staff = user.isStaff();

                // Definitely a better way to do this

                if (owner && LazyUtils.anyStartsWithIgnoreCase(message, "@oc", "@dc"))
                    user.chat(Channel.OWNER, trimmed);
                else if (admin && StringUtils.startsWithIgnoreCase(message, "@ac"))
                    user.chat(Channel.ADMIN, trimmed);
                else if (staff && StringUtils.startsWithIgnoreCase(message, "@sc"))
                    user.chat(Channel.STAFF, trimmed);
                else if (owner && StringUtils.startsWithIgnoreCase(message, "@lc"))
                    user.chat(Channel.LOCAL, trimmed);
                else if (staff && StringUtils.startsWithIgnoreCase(message, "@gc"))
                    user.chat(Channel.GLOBAL, trimmed);
                else if (owner && user.inOwnerChat())
                    user.chat(Channel.OWNER, message);
                else if (admin && user.inAdminChat())
                    user.chat(Channel.ADMIN, message);
                else if (staff && user.inStaffChat())
                    user.chat(Channel.STAFF, message);
                else if (user.inLocalChat())
                    user.chat(Channel.LOCAL, message);
                else
                    user.chat(Channel.GLOBAL, message);
            }
        }
    }

}
