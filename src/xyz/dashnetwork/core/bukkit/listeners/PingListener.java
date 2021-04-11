package xyz.dashnetwork.core.bukkit.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.util.CachedServerIcon;
import xyz.dashnetwork.core.bukkit.Core;
import xyz.dashnetwork.core.bukkit.events.UserPacketEvent;
import xyz.dashnetwork.core.utils.ColorUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class PingListener implements Listener {

    private CachedServerIcon icon;

    public PingListener() {
        try {
            ClassLoader loader = Core.class.getClassLoader();
            BufferedImage image = ImageIO.read(loader.getResource("assets/warning.png"));

            icon = Bukkit.loadServerIcon(image);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        String motd = "&c&l!!! &cYou using the wrong server IP &c&l!!!" +
                "\n&7Use &6play.dashnetwork.xyz &7to join";

        event.setMaxPlayers(-1);
        event.setMotd(ColorUtils.translate(motd));
        event.setServerIcon(icon);
    }

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
