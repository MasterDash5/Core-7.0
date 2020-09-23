package dashnetwork.core.bungee.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import dashnetwork.core.bungee.utils.MessageUtils;
import dashnetwork.core.bungee.utils.PermissionType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChannelListener implements Listener {

    @EventHandler
    public void onPluginMessage(PluginMessageEvent event) {
        String tag = event.getTag();
        ByteArrayDataInput input = ByteStreams.newDataInput(event.getData());

        switch (tag) {
            case "dashnetwork:broadcast":
                event.setCancelled(true);

                PermissionType permission = PermissionType.fromId(input.readByte());
                String message = input.readUTF();

                MessageUtils.broadcast(permission, message);

                break;
            case "minecraft:brand":
                Connection connection = event.getReceiver();

                if (connection instanceof ProxiedPlayer) {
                    event.setCancelled(true);

                    ProxiedPlayer player = (ProxiedPlayer) connection;
                    String serverName = "DashNetwork - " + player.getServer().getInfo().getName();
                    ByteBuf buffer = Unpooled.buffer();
                    byte[] array = serverName.getBytes();

                    int length = array.length;
                    int part;

                    while (true) {
                        part = length & 0x7F;
                        length >>>= 7;

                        if (length != 0)
                            part |= 0x80;

                        buffer.writeByte(part);

                        if (length == 0)
                            break;
                    }

                    player.sendData("minecraft:brand", buffer.array());
                }

                break;
        }

    }

}
