package dashnetwork.core.bungee.events;

import dashnetwork.core.bungee.utils.User;
import net.md_5.bungee.api.plugin.Event;

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
