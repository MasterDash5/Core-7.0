package xyz.dashnetwork.core.bungee.listeners;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import xyz.dashnetwork.core.bungee.Core;
import xyz.dashnetwork.core.bungee.utils.DataUtils;
import xyz.dashnetwork.core.bungee.utils.MessageUtils;
import xyz.dashnetwork.core.bungee.utils.NameUtils;
import xyz.dashnetwork.core.bungee.utils.User;
import xyz.dashnetwork.core.utils.ColorUtils;
import xyz.dashnetwork.core.utils.MessageBuilder;
import xyz.dashnetwork.core.utils.ProtocolVersion;
import xyz.dashnetwork.core.utils.StringUtils;

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
        boolean hex = ProtocolVersion.fromId(event.getConnection().getVersion()).isNewerThanOrEqual(ProtocolVersion.v1_16);

        int online = players.getOnline();

        for (User user : User.getUsers(false))
            if (user.isVanished())
                online--;

        String software = "DashNetwork 1.7 - 1.16";

        List<ServerPing.PlayerInfo> list = new ArrayList<>();
        list.add(new ServerPing.PlayerInfo(ColorUtils.translate("&f             &6&n&lDashNetwork"), UUID.randomUUID()));
        list.add(new ServerPing.PlayerInfo(ColorUtils.translate("&6&lMinecraft &7play.dashnetwork.xyz"), UUID.randomUUID()));
        list.add(new ServerPing.PlayerInfo(ColorUtils.translate("&6&lDiscord &7discord.dashnetwork.xyz"), UUID.randomUUID()));

        String hexMotd = "§x§f§c§5§7§1§7§lD§x§f§b§5§d§1§6§la§x§f§c§6§4§1§5§ls§x§f§c§6§a§1§4§lh§x§f§c§7§1§1§3§lN§x§f§c§7§7§1§1§le§x§f§c§7§e§1§0§lt§x§f§c§8§4§0§f§lw§x§f§c§8§b§0§e§lo§x§f§c§9§1§0§d§lr§x§f§c§9§8§0§b§lk";

        component.setText(ColorUtils.translate("&6&lDashNetwork &6&l»&7 [1.7 - 1.16]"));
        component.addExtra(ColorUtils.translate("\n&c&lServer IP: &6play.dashnetwork.xyz"));

        response.setVersion(new ServerPing.Protocol(software, response.getVersion().getProtocol()));
        response.setPlayers(new ServerPing.Players(players.getMax(), online, list.toArray(new ServerPing.PlayerInfo[list.size()])));
        response.setDescriptionComponent(component);

        PendingConnection connection = event.getConnection();
        String address = ((InetSocketAddress) connection.getSocketAddress()).getAddress().getHostAddress();
        boolean skipPingspy = false;

        for (User user : User.getUsers(true))
            if (address.equals(user.getAddress()))
                skipPingspy = true;

        if (DataUtils.getIpbans().containsKey(address))
            skipPingspy = true;

        if (!skipPingspy && !recentPings.contains(address)) {
            recentPings.add(address);

            bungee.getScheduler().schedule(plugin, () -> recentPings.remove(address), 1, TimeUnit.MINUTES);
            bungee.getScheduler().runAsync(plugin, () -> {
                Map<String, List<String>> ips = DataUtils.getIps();

                if (ips.containsKey(address)) {
                    List<String> names = new ArrayList<>();

                    for (String uuid : ips.get(address))
                        names.add(NameUtils.getUsername(UUID.fromString(uuid)));

                    String fromNames = StringUtils.fromList(names, false, true);

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
