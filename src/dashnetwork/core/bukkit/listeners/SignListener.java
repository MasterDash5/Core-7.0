package dashnetwork.core.bukkit.listeners;

import dashnetwork.core.bukkit.utils.User;
import dashnetwork.core.utils.EnumUtils;
import dashnetwork.core.utils.MessageBuilder;
import dashnetwork.core.utils.StringUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignListener implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);

        Location location = event.getBlock().getLocation();
        String dimension = EnumUtils.toName(location.getWorld().getEnvironment());
        String coordinates = location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ();

        StringBuilder lines = new StringBuilder();

        for (String line : event.getLines()) {
            if (!StringUtils.clear(line, " ").isEmpty()) {
                if (lines.length() > 0)
                    lines.append("\n");
                lines.append("&6" + line);
            }
        }

        if (lines.length() > 0) {
            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» ");
            message.append("&6" + user.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + player.getName());
            message.append(" ");
            message.append("&7placed sign " + dimension + " " + coordinates).clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, coordinates).hoverEvent(HoverEvent.Action.SHOW_TEXT, lines.toString());

            for (User online : User.getUsers(false))
                if (online.inBookSpy())
                    online.sendMessage(message.build());
        }
    }

}
