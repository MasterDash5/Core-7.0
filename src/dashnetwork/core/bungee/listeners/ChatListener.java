package dashnetwork.core.bungee.listeners;

import dashnetwork.core.bungee.utils.*;
import dashnetwork.core.utils.ColorUtils;
import dashnetwork.core.utils.LazyUtils;
import dashnetwork.core.utils.StringUtils;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(ChatEvent event) {
        Connection connection = event.getSender();

        if (connection instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) connection;
            User user = User.getUser(player);
            String message = event.getMessage();

            if (event.isProxyCommand() || event.isCommand()) {
                for (User online : User.getUsers())
                    if (online.inCommandSpy())
                        MessageUtils.message(online, ColorUtils.translate("&c&lCS &6" + player.getDisplayName() + " &e&l> &b") + message);
            } else {
                event.setCancelled(true);

                String trimmed = message.length() > 3 ? message.substring((message.substring(3).startsWith(" ") ? 4 : 3)) : "";

                if (user.inOwnerChat())
                    ownerChat(player, message);
                else if (user.isOwner() && LazyUtils.anyStartsWith(message.toLowerCase(), "@oc", "@dc"))
                    ownerChat(player, trimmed);
                else if (user.inAdminChat())
                    adminChat(player, message);
                else if (user.isAdmin() && StringUtils.startsWithIgnoreCase(message, "@ac"))
                    adminChat(player, trimmed);
                else if (user.inStaffChat())
                    staffChat(player, message);
                else if (user.isStaff() && StringUtils.startsWithIgnoreCase(message, "@sc"))
                    staffChat(player, trimmed);
                else {
                    if (user.allowedChatColors())
                        message = ColorUtils.translate(message);

                    MessageUtils.broadcast(PermissionType.NONE, ColorUtils.translate(player.getDisplayName() + " &e&l>&f ") + message);
                }
            }
        }
    }

    private void ownerChat(ProxiedPlayer player, String input) {
        MessageUtils.broadcast(PermissionType.OWNER, ColorUtils.translate("&9&lOwner &6" + player.getDisplayName() + " &6&l> &c" + input));
    }

    private void adminChat(ProxiedPlayer player, String input) {
        MessageUtils.broadcast(PermissionType.ADMIN, ColorUtils.translate("&9&lAdmin &6" + player.getDisplayName() + " &6&l> &3" + input));
    }

    private void staffChat(ProxiedPlayer player, String input) {
        MessageUtils.broadcast(PermissionType.STAFF, ColorUtils.translate("&9&lStaff &6" + player.getDisplayName() + " &6&l> &6" + input));

        // if (Bukkit.getPluginManager().isPluginEnabled("DiscordSRV"))
        //     DiscordSRV.getPlugin().processChatMessage(player, input, "staffchat", false);
    }

}