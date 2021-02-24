package dashnetwork.core.bungee.listeners;

import dashnetwork.core.bungee.Core;
import dashnetwork.core.bungee.utils.DataUtils;
import dashnetwork.core.bungee.utils.MessageUtils;
import dashnetwork.core.bungee.utils.Messages;
import dashnetwork.core.bungee.utils.User;
import dashnetwork.core.utils.MessageBuilder;
import dashnetwork.core.utils.StringUtils;
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

public class JoinListener implements Listener {

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();
        User user = User.getUser(player);
        String uuid = player.getUniqueId().toString();
        String name = player.getName();
        String displayname = user.getDisplayName();
        String address = ((InetSocketAddress) player.getSocketAddress()).getAddress().getHostAddress();

        Map<String, List<String>> ips = DataUtils.getIps();
        Map<String, String> names = DataUtils.getNames();
        List<String> uuids = ips.getOrDefault(address, new ArrayList<>());

        if (!uuids.contains(uuid))
            uuids.add(uuid);

        names.put(player.getUniqueId().toString(), player.getName());
        ips.put(address, uuids);

        BungeeCord.getInstance().getScheduler().runAsync(Core.getInstance(), () -> {
            List<String> alts = new ArrayList<>();

            for (String account : uuids)
                if (!account.equals(uuid))
                    alts.add(names.get(account));

            if (!alts.isEmpty()) {
                MessageBuilder message = new MessageBuilder();
                message.append("&c&lAlt ");
                message.append("&6" + displayname).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + name);
                message.append("&c&l > ");
                message.append("&7hover for list of &6" + alts.size() + " alts").hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + StringUtils.fromList(alts, false, true));

                for (User online : User.getUsers(true))
                    if (online.inAltSpy())
                        MessageUtils.message(online, message.build());
            }
        });

        Messages.joinServer(name, displayname);
    }

}
