package dashnetwork.core.listeners;

import dashnetwork.core.utils.ColorUtils;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PingListener implements Listener {

    @EventHandler
    public void onProxyPing(ProxyPingEvent event) {
        ServerPing response = event.getResponse();
        ServerPing.Players players = response.getPlayers();
        TextComponent component = new TextComponent();

        String software = "DashNetwork 1.7 - 1.16";
        List<ServerPing.PlayerInfo> list = new ArrayList<>();

        list.add(new ServerPing.PlayerInfo(ColorUtils.translate("&f              &6&n&lDashNetwork"), UUID.randomUUID()));
        list.add(new ServerPing.PlayerInfo(ColorUtils.translate("&6&lMinecraft &7play.dashnetwork.xyz"), UUID.randomUUID()));
        list.add(new ServerPing.PlayerInfo(ColorUtils.translate("&6&lDiscord &7discord.dashnetwork.xyz"), UUID.randomUUID()));

        component.setText(ColorUtils.translate("&6&lDashNetwork »&7 [1.7 - 1.16]"));
        component.addExtra(ColorUtils.translate("\n&c&lNew Server IP: &6play.dashnetwork.xyz"));

        response.setVersion(new ServerPing.Protocol(software, response.getVersion().getProtocol()));
        response.setPlayers(new ServerPing.Players(players.getMax(), players.getOnline(), list.toArray(new ServerPing.PlayerInfo[list.size()])));
        response.setDescriptionComponent(component);
    }

}
