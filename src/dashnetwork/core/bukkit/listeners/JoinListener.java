package dashnetwork.core.bukkit.listeners;

import dashnetwork.core.bukkit.utils.DataUtils;
import dashnetwork.core.bukkit.utils.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.Map;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = event.getPlayer();
        User user = User.getUser(player);
        String uuid = player.getUniqueId().toString();
        Map<String, String> displaynameQueue = DataUtils.getDisplaynameQueue();
        List<String> vanishQueue = DataUtils.getVanishQueue();

        if (displaynameQueue.containsKey(uuid)) {
            user.setDisplayName(displaynameQueue.get(uuid));

            displaynameQueue.remove(uuid);
        }

        if (vanishQueue.contains(uuid)) {
            user.setVanished(true);

            vanishQueue.remove(uuid);
        }

        if (!user.isStaff())
            for (User online : User.getUsers(false)) // Newly made users will have isVanished() set to false
                if (!online.equals(user) && online.isVanished())
                    player.hidePlayer(online.getPlayer());

        player.setPlayerListName(user.getDisplayName());
    }

}
