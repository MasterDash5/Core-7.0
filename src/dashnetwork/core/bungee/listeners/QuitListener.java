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

public class QuitListener implements Listener {

    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        User user = User.getUser(player);

        MessageBuilder message = new MessageBuilder();
        message.append("&c&l» &6" + user.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + player.getName());
        message.append("&c left the server.");

        MessageUtils.broadcast(PermissionType.NONE, message.build());

        user.remove();
    }

}
