package dashnetwork.core.bukkit.utils;

public class UserAddon {

    public UserAddon(User user) {
        user.addAddon(this);
    }

}
