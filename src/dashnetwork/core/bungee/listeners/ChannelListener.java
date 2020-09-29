package dashnetwork.core.bungee.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import dashnetwork.core.bungee.utils.MessageUtils;
import dashnetwork.core.bungee.utils.PermissionType;
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
        }

    }

}
