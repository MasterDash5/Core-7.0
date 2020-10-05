package dashnetwork.core.bungee.listeners;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class KickListener implements Listener {

    private static BungeeCord bungee = BungeeCord.getInstance();

    @EventHandler
    public void onServerKick(ServerKickEvent event) {
        ProxiedPlayer player = event.getPlayer();
        boolean lobby = event.getKickedFrom().getName().equalsIgnoreCase("lobby");

        player.sendMessage(event.getKickReasonComponent());

        event.setCancelled(true);

        if (lobby)
            event.setCancelServer(bungee.getServerInfo("creative"));
        else
            event.setCancelServer(bungee.getServerInfo("lobby"));
    }

}
