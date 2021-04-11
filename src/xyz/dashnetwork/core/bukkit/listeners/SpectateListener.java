package xyz.dashnetwork.core.bukkit.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import xyz.dashnetwork.core.bukkit.events.UserPacketEvent;
import xyz.dashnetwork.core.bukkit.utils.User;

public class SpectateListener implements Listener {

    @EventHandler
    public void onUserPacket(UserPacketEvent event) {
        PacketContainer packet = event.getPacket();

        if (packet.getType().equals(PacketType.Play.Client.USE_ENTITY) && event.getUser().getPlayer().getGameMode().equals(GameMode.SPECTATOR)) {
            Entity entity = packet.getEntityModifier(event.getPacketEvent()).read(0);

            if (entity instanceof Player) {
                User user = User.getUser((Player) entity);

                if (user.isDash())
                    event.setCancelled(true);
            }
        }
    }

}
