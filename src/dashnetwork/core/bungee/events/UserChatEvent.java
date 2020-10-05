package dashnetwork.core.bungee.events;

import dashnetwork.core.bungee.utils.PermissionType;
import dashnetwork.core.bungee.utils.User;
import net.md_5.bungee.api.plugin.Event;

public class UserChatEvent extends Event {

    private User user;
    private PermissionType channel;
    private String message;

    public UserChatEvent(User user, PermissionType channel, String message) {
        this.user = user;
        this.channel = channel;
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public PermissionType getChannel() {
        return channel;
    }

    public String getMessage() {
        return message;
    }

}
