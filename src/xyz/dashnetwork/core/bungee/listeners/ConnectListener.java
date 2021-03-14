package xyz.dashnetwork.core.bungee.listeners;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import xyz.dashnetwork.core.bungee.utils.User;

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
    }

}
