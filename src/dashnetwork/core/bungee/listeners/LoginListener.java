package dashnetwork.core.bungee.listeners;

import dashnetwork.core.bungee.Core;
import dashnetwork.core.bungee.utils.*;
import dashnetwork.core.utils.ListUtils;
import net.md_5.bungee.BungeeCord;
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

    private BungeeCord bungee = BungeeCord.getInstance();
    private Core plugin = Core.getInstance();

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        String name = player.getName();
        String address = ((InetSocketAddress) player.getSocketAddress()).getAddress().getHostAddress();

        Map<String, List<String>> ips = DataUtils.getIps();
        Map<String, String> names = DataUtils.getNames();
        List<String> uuids = ips.getOrDefault(address, new ArrayList<>());

        if (!uuids.contains(uuid))
            uuids.add(uuid);

        names.put(player.getUniqueId().toString(), player.getName());
        ips.put(address, uuids);

        bungee.getScheduler().runAsync(plugin, () -> {
            List<String> alts = new ArrayList<>();

            for (String account : uuids)
                if (!account.equals(uuid))
                    alts.add(names.get(account));

            if (!alts.isEmpty()) {
                MessageBuilder message = new MessageBuilder();
                message.append("&c&lAlt &6" + player.getDisplayName() + " &c&l>&7 hover for list of &6" + alts.size() + " alts").hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + ListUtils.fromList(alts, false, true));

                for (User online : User.getUsers())
                    if (online.inAltSpy())
                        MessageUtils.message(online, message.build());
            }
        });

        MessageBuilder message = new MessageBuilder();
        message.append("&6&l» &e" + player.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&e" + name);
        message.append("&6 joined the server.");

        MessageUtils.broadcast(PermissionType.NONE, message.build());
    }

}
