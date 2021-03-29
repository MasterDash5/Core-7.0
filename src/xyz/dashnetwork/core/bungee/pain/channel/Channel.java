package xyz.dashnetwork.core.bungee.pain.channel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Channel {

    private static List<Channel> channels = new ArrayList<>();
    private String name;

    public Channel(String name) {
        this.name = name;

        channels.add(this);
    }

    public static List<Channel> getChannels() {
        return channels;
    }

    public static void stopAll() {
        channels.clear();
    }

    public static Channel getChannel(String name) {
        for (Channel channel : channels)
            if (channel.name.equalsIgnoreCase(name))
                return channel;
        return null;
    }

    public abstract void onChannel(DataInputStream input, DataOutputStream output) throws IOException;

}
