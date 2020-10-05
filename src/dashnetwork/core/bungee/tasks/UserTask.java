package dashnetwork.core.bungee.tasks;

import dashnetwork.core.bungee.utils.User;
import net.md_5.bungee.BungeeCord;

public class UserTask implements Runnable {

    private static BungeeCord bungee = BungeeCord.getInstance();

    @Override
    public void run() {
        for (User user : User.getUsers(false))
            if (!bungee.getPlayers().contains(user.getPlayer()))
                user.remove();
    }

}
