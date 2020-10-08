package dashnetwork.core.bungee.listeners;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dashnetwork.core.bungee.Core;
import dashnetwork.core.bungee.utils.DataUtils;
import dashnetwork.core.bungee.utils.MessageUtils;
import dashnetwork.core.bungee.utils.PermissionType;
import dashnetwork.core.bungee.utils.User;
import dashnetwork.core.utils.ListUtils;
import dashnetwork.core.utils.MessageBuilder;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConnectListener implements Listener {

    private BungeeCord bungee = BungeeCord.getInstance();

    @EventHandler
    public void onServerConnnect(ServerConnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        User user = User.getUser(player);
        String uuid = player.getUniqueId().toString();
        ServerInfo serverInfo = event.getTarget();

        ByteArrayDataOutput displaynameOut = ByteStreams.newDataOutput();
        displaynameOut.writeUTF(uuid);
        displaynameOut.writeUTF(user.getDisplayName());

        serverInfo.sendData("dn:displayname", displaynameOut.toByteArray());

        if (user.isVanished()) {
            ByteArrayDataOutput vanishOut = ByteStreams.newDataOutput();
            vanishOut.writeUTF(uuid);
            vanishOut.writeBoolean(true);

            serverInfo.sendData("dn:vanish", vanishOut.toByteArray());
        }

        if (user.inSignSpy()) {
            ByteArrayDataOutput vanishOut = ByteStreams.newDataOutput();
            vanishOut.writeUTF(uuid);
            vanishOut.writeBoolean(true);

            serverInfo.sendData("dn:signspy", vanishOut.toByteArray());
        }

        if (event.getReason().equals(ServerConnectEvent.Reason.JOIN_PROXY)) {
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

            bungee.getScheduler().runAsync(Core.getInstance(), () -> {
                List<String> alts = new ArrayList<>();

                for (String account : uuids)
                    if (!account.equals(uuid))
                        alts.add(names.get(account));

                if (!alts.isEmpty()) {
                    MessageBuilder message = new MessageBuilder();
                    message.append("&c&lAlt ");
                    message.append("&6" + displayname).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + name);
                    message.append("&c&l > ");
                    message.append("&7hover for list of &6" + alts.size() + " alts").hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + ListUtils.fromList(alts, false, true));

                    for (User online : User.getUsers(true))
                        if (online.inAltSpy())
                            MessageUtils.message(online, message.build());
                }
            });

            MessageBuilder message = new MessageBuilder();
            message.append("&a&l» ");
            message.append("&6" + displayname).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + name);
            message.append("&a joined the server.");

            MessageUtils.broadcast(PermissionType.NONE, message.build());
        }
    }

}
