package dashnetwork.core.bukkit.listeners;

import dashnetwork.core.bukkit.Core;
import dashnetwork.core.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.util.CachedServerIcon;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class PingListener implements Listener {

    private CachedServerIcon icon;

    public PingListener() {
        try {
            ClassLoader loader = Core.class.getClassLoader();
            BufferedImage image = ImageIO.read(loader.getResource("warning-icon.png"));

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

}