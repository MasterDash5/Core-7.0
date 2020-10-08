package dashnetwork.core.bungee.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dashnetwork.core.bungee.utils.MessageUtils;
import dashnetwork.core.bungee.utils.PermissionType;
import dashnetwork.core.bungee.utils.User;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Collection;

public class ChannelListener implements Listener {

    private static BungeeCord bungee = BungeeCord.getInstance();

    @EventHandler
    public void onPluginMessage(PluginMessageEvent event) {
        String tag = event.getTag();
        ByteArrayDataInput input = ByteStreams.newDataInput(event.getData());
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        Connection connection = event.getReceiver();

        if (connection instanceof ProxiedPlayer) {
            ProxiedPlayer target = (ProxiedPlayer) connection;

            switch (tag) {
                case "dn:broadcast":
                    event.setCancelled(true);

                    PermissionType permission = PermissionType.fromId(input.readByte());
                    String message = input.readUTF();

                    MessageUtils.broadcast(permission, message);

                    break;
                case "dn:online":
                    event.setCancelled(true);

                    int total = bungee.getOnlineCount();
                    int vanished = 0;

                    for (User user : User.getUsers(false))
                        if (user.isVanished())
                            vanished++;

                    output.writeInt(total);
                    output.writeInt(vanished);

                    target.getServer().sendData("dn:online", output.toByteArray());

                    break;
                case "bungeecord:main":
                case "BungeeCord":
                    String request = input.readUTF();

                    if (request.equals("PlayerCount")) {
                        event.setCancelled(true);

                        String selected = input.readUTF();
                        int online;

                        if (selected.equals("ALL")) {
                            online = bungee.getOnlineCount();

                            for (User user : User.getUsers(false))
                                if (user.isVanished())
                                    online--;

                            for (ServerInfo server : bungee.getServers().values()) {
                                Collection<ProxiedPlayer> players = server.getPlayers();
                                int each = players.size();

                                for (ProxiedPlayer player : players) {
                                    User user = User.getUser(player);

                                    if (user.isVanished())
                                        each--;
                                }

                                ByteArrayDataOutput eachOutput = ByteStreams.newDataOutput();
                                eachOutput.writeUTF("PlayerCount");
                                eachOutput.writeUTF(server.getName());
                                eachOutput.writeInt(each);

                                target.getServer().sendData(tag, eachOutput.toByteArray());
                            }
                        } else {
                            Collection<ProxiedPlayer> players = bungee.getServerInfo(selected).getPlayers();

                            online = players.size();

                            for (ProxiedPlayer player : players) {
                                User user = User.getUser(player);

                                if (user.isVanished())
                                    online--;
                            }
                        }

                        output.writeUTF("PlayerCount");
                        output.writeUTF(selected);
                        output.writeInt(online);

                        target.getServer().sendData(tag, output.toByteArray());
                    }
            }
        }

    }

}
