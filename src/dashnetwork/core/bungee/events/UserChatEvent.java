package dashnetwork.core.bungee.events;

import dashnetwork.core.bungee.utils.User;
import dashnetwork.core.utils.Channel;
import net.md_5.bungee.api.plugin.Event;

public class UserChatEvent extends Event {

    private User user;
    private Channel channel;
    private String message;

    public UserChatEvent(User user, Channel channel, String message) {
        this.user = user;
        this.channel = channel;
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public Channel getChannel() {
        return channel;
    }

    public String getMessage() {
        return message;
    }

}
