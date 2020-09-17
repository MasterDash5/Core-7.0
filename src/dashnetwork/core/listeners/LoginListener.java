package dashnetwork.core.listeners;

import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoginListener implements Listener {

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        String name = player.getName();
        String address = ((InetSocketAddress) player.getSocketAddress()).getAddress().getHostAddress();

        Map<String, List<String>> ips = DataUtils.getIps();
        List<String> uuids = ips.getOrDefault(address, new ArrayList<>());

        if (!uuids.contains(uuid))
            uuids.add(uuid);

        DataUtils.getNames().put(player.getUniqueId().toString(), player.getName());
        ips.put(address, uuids);

        MessageBuilder message = new MessageBuilder();
        message.append("&e" + player.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&e" + name);
        message.append("&e joined the game.");

        MessageUtils.broadcast(PermissionType.NONE, message.build());
    }

}
