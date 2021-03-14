package xyz.dashnetwork.core.bukkit.tasks;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.dashnetwork.core.bukkit.utils.User;

public class SpinTask implements Runnable {

    @Override
    public void run() {
        for (User user : User.getUsers(false)) {
            if (user.isSpinning()) {
                Player player = user.getPlayer();
                Location location = player.getLocation().clone();

                location.setYaw(location.getYaw() + 1);
                player.teleport(location);
            }
        }
    }

}
