package xyz.dashnetwork.core.bukkit.listeners;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import xyz.dashnetwork.core.bukkit.utils.User;
import xyz.dashnetwork.core.utils.ColorUtils;
import xyz.dashnetwork.core.utils.EnumUtils;
import xyz.dashnetwork.core.utils.MessageBuilder;
import xyz.dashnetwork.core.utils.StringUtils;

public class SignListener implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);

        Location location = event.getBlock().getLocation();
        String dimension = EnumUtils.toName(location.getWorld().getEnvironment());
        String coordinates = location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ();

        StringBuilder lines = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            String line = event.getLine(i);

            if (!StringUtils.clear(line, " ").isEmpty()) {
                if (lines.length() > 0)
                    lines.append("\n");
                lines.append("&6" + line);
            }

            event.setLine(i, ColorUtils.translate(line));
        }

        if (lines.length() > 0) {
            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» ");
            message.append("&6" + user.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + player.getName());
            message.append(" ");
            message.append("&7placed sign " + dimension + " " + coordinates).clickEvent(ClickEvent.Action.SUGGEST_COMMAND, coordinates).hoverEvent(HoverEvent.Action.SHOW_TEXT, lines.toString());

            for (User online : User.getUsers(false))
                if (online.inSignSpy())
                    online.sendMessage(message.build());
        }
    }

}
