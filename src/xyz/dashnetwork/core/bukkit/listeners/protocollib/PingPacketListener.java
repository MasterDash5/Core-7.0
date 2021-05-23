package xyz.dashnetwork.core.bukkit.listeners.protocollib;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import xyz.dashnetwork.core.bukkit.events.UserPacketEvent;

public class PingPacketListener implements Listener {

    @EventHandler
    public void onUserPacket(UserPacketEvent event) {
        if (event.getPacket().getType().equals(PacketType.Status.Server.SERVER_INFO)) {
            WrappedServerPing serverPing = event.getPacket().getServerPings().read(0);

            serverPing.setPlayersVisible(false);
            serverPing.setVersionName("play.dashnetwork.xyz");
            serverPing.setVersionProtocol(0);

            event.getPacket().getServerPings().write(0, serverPing);
        }
    }

}
