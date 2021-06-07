package xyz.dashnetwork.core.bungee.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.chat.ComponentSerializer;
import net.md_5.bungee.event.EventHandler;
import xyz.dashnetwork.core.bungee.utils.F3Utils;
import xyz.dashnetwork.core.bungee.utils.MessageUtils;
import xyz.dashnetwork.core.bungee.utils.PermissionType;
import xyz.dashnetwork.core.bungee.utils.User;

import java.util.Collection;

public class ChannelListener implements Listener {

    private static BungeeCord bungee = BungeeCord.getInstance();

    @EventHandler
    public void onPluginMessage(PluginMessageEvent event) {
        String tag = event.getTag();
        ByteArrayDataInput input = ByteStreams.newDataInput(event.getData());
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        Connection receiver = event.getReceiver();
        Connection sender = event.getSender();

        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer target = (ProxiedPlayer) sender;

            switch (tag) {
                case "WDL|INIT":
                case "wdl:init":
                    event.setCancelled(true);

                    output.writeInt(0);
                    output.writeBoolean(User.getUser(target).isOwner());

                    String channel = tag.contains("|") ? "WDL|CONTROL" : "wdl:control";

                    target.sendData(channel, output.toByteArray());
            }
        }

        if (receiver instanceof ProxiedPlayer) {
            ProxiedPlayer target = (ProxiedPlayer) receiver;

            switch (tag) {
                case "minecraft:brand":
                    event.setCancelled(true);

                    target.sendData(tag, F3Utils.toBytes("play.dashnetwork.xyz"));

                    break;
                case "dn:broadcast":
                    event.setCancelled(true);

                    PermissionType permission = PermissionType.fromId(input.readByte());
                    String message = input.readUTF();
                    boolean json = input.readBoolean();

                    if (json)
                        MessageUtils.broadcast(permission, ComponentSerializer.parse(message));
                    else
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
                case "BungeeCord":
                case "bungeecord:main":
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
                            ServerInfo server = bungee.getServerInfo(selected);

                            if (server != null) {
                                Collection<ProxiedPlayer> players = server.getPlayers();

                                if (players != null) {
                                    online = players.size();

                                    for (ProxiedPlayer player : players) {
                                        User user = User.getUser(player);

                                        if (user.isVanished())
                                            online--;
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
        }

    }

}
