package xyz.dashnetwork.core.bungee.pain.channel.channels;

import net.md_5.bungee.BungeeCord;
import xyz.dashnetwork.core.bungee.pain.channel.Channel;
import xyz.dashnetwork.core.bungee.utils.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ChannelOnline extends Channel {

    public ChannelOnline() {
        super("online");
    }

    @Override
    public void onChannel(DataInputStream input, DataOutputStream output) throws IOException {
        int total = BungeeCord.getInstance().getOnlineCount();
        int vanished = 0;

        for (User user : User.getUsers(true))
            if (user.isVanished())
                vanished++;

        output.writeInt(total);
        output.writeInt(vanished);
    }

}
