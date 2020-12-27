package dashnetwork.core.bukkit.tasks;

import dashnetwork.core.bukkit.utils.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class SpectateTask implements Runnable {

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Entity entity = player.getSpectatorTarget();

            if (entity instanceof Player) {
                Player target = (Player) entity;
                User user = User.getUser(target);

                if (user.isDash())
                    player.setSpectatorTarget(player);
            }
        }
    }

}
