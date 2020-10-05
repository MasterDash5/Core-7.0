package dashnetwork.core.bukkit.tasks;

import dashnetwork.core.bukkit.utils.User;
import org.bukkit.Bukkit;

public class UserTask implements Runnable {

    @Override
    public void run() {
        for (User user : User.getUsers(false))
            if (!Bukkit.getOnlinePlayers().contains(user.getPlayer()))
                user.remove();
    }

}
