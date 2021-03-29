package xyz.dashnetwork.core.bungee.pain.channel.channels;

import xyz.dashnetwork.core.bungee.pain.channel.Channel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ChannelTest extends Channel {

    public ChannelTest() {
        super("test");
    }

    @Override
    public void onChannel(DataInputStream input, DataOutputStream output) throws IOException {
        System.out.println("Channel Test");

        output.writeInt(54545454);
        output.writeUTF("Join our Minecraft server!");
    }

}
