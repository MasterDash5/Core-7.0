package dashnetwork.core.bungee.listeners;

import dashnetwork.core.bungee.utils.*;
import dashnetwork.core.utils.ColorUtils;
import dashnetwork.core.utils.LazyUtils;
import dashnetwork.core.utils.StringUtils;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatListener implements Listener {

    private LuckPerms lp = LuckPermsProvider.get();

    @EventHandler
    public void onChat(ChatEvent event) {
        Connection connection = event.getSender();

        if (connection instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) connection;
            User user = User.getUser(player);
            String message = event.getMessage();

            if (event.isProxyCommand() || event.isCommand()) {
                for (User online : User.getUsers(true))
                    if (online.inCommandSpy())
                        MessageUtils.message(online, "&c&lCS &6" + user.getDisplayName() + " &e&l> &b" + message);
            } else {
                event.setCancelled(true);

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
                else {
                    if (user.allowedChatColors())
                        message = ColorUtils.translate(message);

                    for (User online : User.getUsers(true))
                        online.sendMessage(ColorUtils.translate(user.getDisplayName() + " &e&l>&f ") + message);
                }
            }
        }
    }

    private void localChat(ChatEvent event, String message) {
        event.setCancelled(false);
        event.setMessage(message);
    }

    private void ownerChat(User user, String input) {
        MessageUtils.broadcast(PermissionType.OWNER, "&9&lOwner &6" + user.getDisplayName() + " &6&l> &c" + input);
    }

    private void adminChat(User user, String input) {
        MessageUtils.broadcast(PermissionType.ADMIN, "&9&lAdmin &6" + user.getDisplayName() + " &6&l> &3" + input);
    }

    private void staffChat(User user, String input) {
        MessageUtils.broadcast(PermissionType.STAFF, "&9&lStaff &6" + user.getDisplayName() + " &6&l> &6" + input);

        // if (Bukkit.getPluginManager().isPluginEnabled("DiscordSRV"))
        //     DiscordSRV.getPlugin().processChatMessage(player, input, "staffchat", false);
    }

}
