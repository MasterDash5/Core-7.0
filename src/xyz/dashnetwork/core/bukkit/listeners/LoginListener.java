package xyz.dashnetwork.core.bukkit.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import xyz.dashnetwork.core.bukkit.Core;
import xyz.dashnetwork.core.utils.ColorUtils;

public class LoginListener implements Listener {

    private static String bungee = Core.getBungeeAddress();

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        String address = event.getRealAddress().getHostAddress();

        if (bungee.equalsIgnoreCase("disable"))
            return;

        if (!address.equals(bungee))
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ColorUtils.translate("&6&lDashNetwork\n\n&7Use &6play.dashnetwork.xyz&7 to join"));
    }

}
