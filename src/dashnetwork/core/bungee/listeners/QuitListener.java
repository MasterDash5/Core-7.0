package dashnetwork.core.bungee.listeners;

import dashnetwork.core.bungee.utils.MessageUtils;
import dashnetwork.core.bungee.utils.PermissionType;
import dashnetwork.core.bungee.utils.User;
import dashnetwork.core.utils.MessageBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class QuitListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        User user = User.getUser(player);
        String displayname = user.getDisplayName();
        String name = player.getName();
        MessageBuilder message = new MessageBuilder();

        if (user.isVanished()) {
            message.append("&3&l» ");
            message.append("&6" + displayname).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + name);
            message.append("&3 left the server.");

            MessageUtils.broadcast(PermissionType.STAFF, message.build());
        } else {
            message.append("&c&l» ");
            message.append("&6" + displayname).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + name);
            message.append("&c left the server.");

            MessageUtils.broadcast(PermissionType.NONE, message.build());
        }
    }

}
