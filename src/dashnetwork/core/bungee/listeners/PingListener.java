package dashnetwork.core.bungee.listeners;

import dashnetwork.core.bungee.Core;
import dashnetwork.core.bungee.utils.*;
import dashnetwork.core.utils.ColorUtils;
import dashnetwork.core.utils.ListUtils;
import dashnetwork.core.utils.MessageBuilder;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PingListener implements Listener {

    private static BungeeCord bungee = BungeeCord.getInstance();
    private static Core plugin = Core.getInstance();
    private List<String> recentPings = new ArrayList<>();

    @EventHandler
    public void onProxyPing(ProxyPingEvent event) {
        ServerPing response = event.getResponse();
        ServerPing.Players players = response.getPlayers();
        TextComponent component = new TextComponent();

        String software = "DashNetwork 1.7 - 1.16";
        int online = players.getOnline();

        for (User user : User.getUsers(false))
            if (user.isVanished())
                online--;

        List<ServerPing.PlayerInfo> list = new ArrayList<>();
        list.add(new ServerPing.PlayerInfo(ColorUtils.translate("&f             &6&n&lDashNetwork"), UUID.randomUUID()));
        list.add(new ServerPing.PlayerInfo(ColorUtils.translate("&6&lMinecraft &7play.dashnetwork.xyz"), UUID.randomUUID()));
        list.add(new ServerPing.PlayerInfo(ColorUtils.translate("&6&lDiscord &7discord.dashnetwork.xyz"), UUID.randomUUID()));

        component.setText(ColorUtils.translate("&6&lDashNetwork »&7 [1.7 - 1.16]"));
        component.addExtra(ColorUtils.translate("\n&c&lNew Server IP: &6play.dashnetwork.xyz"));

        response.setVersion(new ServerPing.Protocol(software, response.getVersion().getProtocol()));
        response.setPlayers(new ServerPing.Players(players.getMax(), online, list.toArray(new ServerPing.PlayerInfo[list.size()])));
        response.setDescriptionComponent(component);

        PendingConnection connection = event.getConnection();
        String address = ((InetSocketAddress) connection.getSocketAddress()).getAddress().getHostAddress();

        if (!recentPings.contains(address)) {
            bungee.getScheduler().schedule(plugin, () -> recentPings.remove(address), 5, TimeUnit.MINUTES);

            bungee.getScheduler().runAsync(plugin, () -> {
                Map<String, List<String>> ips = DataUtils.getIps();

                if (ips.containsKey(address)) {
                    List<String> names = new ArrayList<>();

                    for (String uuid : ips.get(address))
                        names.add(DataUtils.getNames().getOrDefault(uuid, "Unknown"));

                    String fromNames = ListUtils.fromList(names, false, true);

                    MessageBuilder message = new MessageBuilder();
                    message.append("&c&lPS &6" + address + " &7pinged the server").hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + fromNames);

                    for (User user : User.getUsers(true))
                        if (user.inPingSpy())
                            MessageUtils.message(user, message.build());
                }
            });
        }
    }

}
