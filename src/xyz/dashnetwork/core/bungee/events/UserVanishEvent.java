package xyz.dashnetwork.core.bungee.events;

import net.md_5.bungee.api.plugin.Event;
import xyz.dashnetwork.core.bungee.utils.User;

public class UserVanishEvent extends Event {

    private User user;
    private boolean vanished;

    public UserVanishEvent(User user, boolean vanished) {
        this.user = user;
        this.vanished = vanished;
    }

    public User getUser() {
        return user;
    }

    public boolean isVanished() {
        return vanished;
    }

}
