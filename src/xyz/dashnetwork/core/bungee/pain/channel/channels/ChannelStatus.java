package xyz.dashnetwork.core.bungee.pain.channel.channels;

import xyz.dashnetwork.core.bungee.pain.channel.Channel;
import xyz.dashnetwork.core.bungee.utils.MessageUtils;
import xyz.dashnetwork.core.bungee.utils.PermissionType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ChannelStatus extends Channel {

    public ChannelStatus() {
        super("status");
    }

    @Override
    public void onChannel(DataInputStream input, DataOutputStream output) throws IOException {
        String server = input.readUTF();
        boolean online = input.readBoolean();

        MessageUtils.broadcast(PermissionType.NONE, "");
    }

}
