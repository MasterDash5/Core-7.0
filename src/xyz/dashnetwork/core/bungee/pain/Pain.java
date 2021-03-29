package xyz.dashnetwork.core.bungee.pain;

import xyz.dashnetwork.core.bungee.pain.channel.Channel;
import xyz.dashnetwork.core.bungee.pain.channel.channels.ChannelBroadcast;
import xyz.dashnetwork.core.bungee.pain.channel.channels.ChannelOnline;
import xyz.dashnetwork.core.bungee.pain.channel.channels.ChannelTest;
import xyz.dashnetwork.core.bungee.pain.listener.Listener;
import xyz.dashnetwork.core.bungee.pain.listener.listeners.ServerListener;

public class Pain {

    public void start() {
        System.out.println("Starting Pain...");

        short port = 25454;

        new ChannelBroadcast();
        new ChannelOnline();
        new ChannelTest();

        new ServerListener(port).start();

        System.out.println("Pain started on 0.0.0.0:" + port);
    }

    public void stop() {
        Listener.stopAll();
        Channel.stopAll();

        System.out.println("Pain successfully shutdown");
    }

}
