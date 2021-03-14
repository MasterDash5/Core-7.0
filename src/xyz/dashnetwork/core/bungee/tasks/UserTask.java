package xyz.dashnetwork.core.bungee.tasks;

import net.md_5.bungee.BungeeCord;
import xyz.dashnetwork.core.bungee.utils.User;

public class UserTask implements Runnable {

    private static BungeeCord bungee = BungeeCord.getInstance();

    @Override
    public void run() {
        for (User user : User.getUsers(false))
            if (!bungee.getPlayers().contains(user.getPlayer()))
                user.remove();
    }

}
