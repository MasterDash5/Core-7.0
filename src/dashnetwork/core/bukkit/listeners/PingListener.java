package dashnetwork.core.bukkit.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class PingListener implements Listener {

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        String motd = "&c&l!!! &cYou using the wrong server IP &c&l!!!" +
                "\n&7Use &6play.dashnetwork.xyz &7to join";

        event.setMaxPlayers(-1);
        event.setMotd(motd);
    }

}
