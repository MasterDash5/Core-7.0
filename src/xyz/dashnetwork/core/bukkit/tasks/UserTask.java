package xyz.dashnetwork.core.bukkit.tasks;

import org.bukkit.Bukkit;
import xyz.dashnetwork.core.bukkit.utils.User;

public class UserTask implements Runnable {

    @Override
    public void run() {
        for (User user : User.getUsers(false))
            if (!Bukkit.getOnlinePlayers().contains(user.getPlayer()))
                user.remove();
    }

}
