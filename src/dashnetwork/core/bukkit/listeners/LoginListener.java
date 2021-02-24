package dashnetwork.core.bukkit.listeners;

import dashnetwork.core.bukkit.Core;
import dashnetwork.core.utils.ColorUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class LoginListener implements Listener {

    private static Core plugin = Core.getInstance();

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        String address = event.getRealAddress().getHostAddress();
        String bungee = plugin.getConfig().getString("bungee-address");

        if (!address.equals(bungee))
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ColorUtils.translate("&6&lDashNetwork\n\n&7Use &6play.dashnetwork.xyz&7 to join"));
    }

}
