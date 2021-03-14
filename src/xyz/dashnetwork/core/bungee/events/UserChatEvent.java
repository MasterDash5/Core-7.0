package xyz.dashnetwork.core.bungee.events;

import net.md_5.bungee.api.plugin.Event;
import xyz.dashnetwork.core.bungee.utils.User;
import xyz.dashnetwork.core.utils.Channel;

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
