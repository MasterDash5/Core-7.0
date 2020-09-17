package dashnetwork.core.bukkit.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class SelectorUtils {

    public static Collection<Player> getPlayers(CommandSender sender, String selector) {
        if (selector.equals("*")) {
            List<Player> players = new ArrayList<>();
            players.addAll(Bukkit.getOnlinePlayers());

            return players;
        }

        List<Player> players = new ArrayList<>();

        for (String split : selector.split(","))
            players.add(getPlayer(sender, split));

        return players;
    }

    public static Player getPlayer(CommandSender sender, String selector) {
        if (selector.equals("@") && sender instanceof Player)
            return (Player) sender;

        try {
            UUID uuid = UUID.fromString(selector);

            return Bukkit.getPlayer(uuid);
        } catch (IllegalArgumentException exception) {
            return Bukkit.getPlayer(selector);
        }
    }

}
