package xyz.dashnetwork.core.bungee.listeners;

import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import xyz.dashnetwork.core.bungee.utils.Messages;
import xyz.dashnetwork.core.bungee.utils.User;

public class QuitListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        User user = User.getUser(event.getPlayer());
        String displayname = user.getDisplayName();
        String name = user.getName();

        if (user.isVanished())
            Messages.leaveServerVanished(name, displayname);
        else
            Messages.leaveServer(name, displayname);
    }

}
