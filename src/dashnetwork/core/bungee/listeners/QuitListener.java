package dashnetwork.core.bungee.listeners;

import dashnetwork.core.utils.MessageBuilder;
import dashnetwork.core.bungee.utils.MessageUtils;
import dashnetwork.core.bungee.utils.PermissionType;
import dashnetwork.core.bungee.utils.User;
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
        message.append("&6&l» &e" + player.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&e" + player.getName());
        message.append("&f left the server.");

        MessageUtils.broadcast(PermissionType.NONE, message.build());

        if (User.hasInstance(player))
            User.getUser(player).remove();
    }

}
