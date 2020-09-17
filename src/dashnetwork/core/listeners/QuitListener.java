package dashnetwork.core.listeners;

import dashnetwork.core.utils.MessageBuilder;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import dashnetwork.core.utils.User;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class QuitListener implements Listener {

    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();

        MessageBuilder message = new MessageBuilder();
        message.append("&e" + player.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&e" + player.getName());
        message.append("&e left the game.");

        MessageUtils.broadcast(PermissionType.NONE, message.build());

        if (User.hasInstance(player))
            User.getUser(player).remove();
    }

}
