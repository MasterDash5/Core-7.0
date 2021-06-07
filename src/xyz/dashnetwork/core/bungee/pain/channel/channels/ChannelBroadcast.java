package xyz.dashnetwork.core.bungee.pain.channel.channels;

import net.md_5.bungee.chat.ComponentSerializer;
import xyz.dashnetwork.core.bungee.pain.channel.Channel;
import xyz.dashnetwork.core.bungee.utils.MessageUtils;
import xyz.dashnetwork.core.bungee.utils.PermissionType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ChannelBroadcast extends Channel {

    public ChannelBroadcast() {
        super("broadcast");
    }

    @Override
    public void onChannel(DataInputStream input, DataOutputStream output) throws IOException {
        PermissionType permission = PermissionType.fromId(input.readByte());
        String message = input.readUTF();
        boolean json = input.readBoolean();

        if (json)
            MessageUtils.broadcast(permission, ComponentSerializer.parse(message));
        else
            MessageUtils.broadcast(permission, message);
    }

}
