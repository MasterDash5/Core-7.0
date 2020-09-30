package dashnetwork.core.bukkit.listeners;

import dashnetwork.core.utils.ColorUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class LoginListener implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        String address = event.getRealAddress().getHostAddress();

        if (!address.equals("172.18.0.1"))
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ColorUtils.translate("&6&lDashNetwork\n\n&7Use &6play.dashnetwork.xyz&7 to join"));
    }

}
