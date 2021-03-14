package xyz.dashnetwork.core.bukkit.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.dashnetwork.core.bukkit.utils.QueueUtils;
import xyz.dashnetwork.core.bukkit.utils.User;

import java.util.List;
import java.util.Map;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = event.getPlayer();
        User user = User.getUser(player);
        String uuid = player.getUniqueId().toString();
        Map<String, String> displaynameQueue = QueueUtils.getDisplaynameQueue();
        List<String> vanishQueue = QueueUtils.getVanishQueue();
        List<String> signspyQueue = QueueUtils.getSignspyQueue();

        if (displaynameQueue.containsKey(uuid)) {
            user.setDisplayName(displaynameQueue.get(uuid));

            displaynameQueue.remove(uuid);
        }

        if (vanishQueue.contains(uuid)) {
            user.setVanished(true);

            vanishQueue.remove(uuid);
        }

        if (signspyQueue.contains(uuid)) {
            user.setSignSpy(true);

            signspyQueue.remove(uuid);
        }

        if (!user.isStaff())
            for (User online : User.getUsers(false)) // Newly made users will have isVanished() set to false
                if (!online.equals(user) && online.isVanished())
                    player.hidePlayer(online.getPlayer());

        player.setPlayerListName(user.getDisplayName());
    }

}
