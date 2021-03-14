package xyz.dashnetwork.core.bukkit.utils;

public class UserAddon {

    private User user;

    protected UserAddon(User user) {
        user.addAddon(this);

        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
