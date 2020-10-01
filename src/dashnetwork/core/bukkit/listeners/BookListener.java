package dashnetwork.core.bukkit.listeners;

import dashnetwork.core.bukkit.Core;
import dashnetwork.core.bukkit.utils.MessageUtils;
import dashnetwork.core.bukkit.utils.User;
import dashnetwork.core.utils.MessageBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class BookListener implements Listener {

    private static Map<Integer, BookMeta> books = new HashMap<>();

    @EventHandler
    public void onPlayerEditBook(PlayerEditBookEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        BookMeta meta = event.getNewBookMeta();
        int available = 0;

        while (books.containsKey(available))
            available++;

        final int id = available;
        String command = "/bookspy " + id;

        books.put(id, meta);

        new BukkitRunnable() {
            @Override
            public void run() {
                books.remove(id);
            }
        }.runTaskLaterAsynchronously(Core.getInstance(), 1200);

        MessageBuilder message = new MessageBuilder();
        message.append("&6&l» ").clickEvent(ClickEvent.Action.RUN_COMMAND, command);
        message.append("&6" + user.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + player.getName()).clickEvent(ClickEvent.Action.RUN_COMMAND, command);
        message.append(" &7edited book. Click to receive copy.").clickEvent(ClickEvent.Action.RUN_COMMAND, command);

        for (User online : User.getUsers(true))
            if (online.inBookSpy())
                MessageUtils.message(online, message.build());
    }

}
