package xyz.dashnetwork.core.bukkit.listeners;

import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.dashnetwork.core.bukkit.utils.User;

public class QuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        for (Wolf wolf : User.getUser(event.getPlayer()).getWolfpack())
            wolf.remove();
    }

}
