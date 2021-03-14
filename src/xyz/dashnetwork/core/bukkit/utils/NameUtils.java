package xyz.dashnetwork.core.bukkit.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NameUtils {

    public static String getDisplayName(CommandSender sender) {
        if (sender instanceof Player)
            return User.getUser((Player) sender).getDisplayName();
        else if (sender.equals(Bukkit.getConsoleSender()))
            return "Console";

        return sender.getName();
    }

    public static String getName(CommandSender sender) {
        if (sender.equals(Bukkit.getConsoleSender()))
            return "Console";

        return sender.getName();
    }

    public static List<String> toNames(Collection<Player> players) {
        List<String> names = new ArrayList<>();

        for (Player player : players)
            names.add(player.getName());

        return names;
    }

    public static List<String> toDisplayNames(Collection<Player> players) {
        List<String> displayNames = new ArrayList<>();

        for (Player player : players)
            displayNames.add(User.getUser(player).getDisplayName());

        return displayNames;
    }

}
