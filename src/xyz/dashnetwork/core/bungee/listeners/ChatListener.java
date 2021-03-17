package xyz.dashnetwork.core.bungee.listeners;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.event.EventHandler;
import xyz.dashnetwork.core.bungee.utils.MessageUtils;
import xyz.dashnetwork.core.bungee.utils.Messages;
import xyz.dashnetwork.core.bungee.utils.User;
import xyz.dashnetwork.core.utils.*;

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

                BaseComponent[] build = broadcast.build();

                for (User online : User.getUsers(true))
                    if (online.inCommandSpy())
                        online.sendMessage(build);

                MessageUtils.message(bungee.getConsole(), build);
            } else {
                event.setCancelled(true);

                if (user.isMuted()) {
                    Messages.muted(user, user.getMute());
                    return;
                }

                if (!user.isStaff())
                    message = ColorUtils.filter(message, true, true, true, true, false, false);

                message = ColorUtils.hex(message);

                String trimmed = message.length() > 3 ? message.substring((message.substring(3).startsWith(" ") ? 4 : 3)) : "";
                boolean owner = user.isOwner();
                boolean admin = user.isAdmin();
                boolean staff = user.isStaff();

                // Not really a better way to do this

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
