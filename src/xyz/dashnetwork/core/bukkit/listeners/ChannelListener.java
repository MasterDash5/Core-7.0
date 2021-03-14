package xyz.dashnetwork.core.bukkit.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import xyz.dashnetwork.core.bukkit.utils.QueueUtils;
import xyz.dashnetwork.core.bukkit.utils.User;

import java.util.UUID;

public class ChannelListener implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] data) {
        ByteArrayDataInput input = ByteStreams.newDataInput(data);
        String uuid = input.readUTF();
        Player target = Bukkit.getPlayer(UUID.fromString(uuid));

        switch (channel) {
            case "dn:displayname":
                String displayname = input.readUTF();

                if (target == null)
                    QueueUtils.getDisplaynameQueue().put(uuid, displayname);
                else {
                    User user = User.getUser(target);

                    user.setDisplayName(displayname);
                    target.setPlayerListName(displayname);
                }

                break;
            case "dn:vanish":
                boolean vanished = input.readBoolean();

                if (target == null)
                    QueueUtils.getVanishQueue().add(uuid);
                else
                    User.getUser(target).setVanished(vanished);

                break;
            case "dn:signspy":
                boolean signspy = input.readBoolean();

                if (target == null)
                    QueueUtils.getSignspyQueue().add(uuid);
                else
                    User.getUser(target).setSignSpy(signspy);

                break;
        }
    }

}
