package dashnetwork.core.bukkit.utils;

import dashnetwork.core.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CompletionUtils {

    public static List<String> players(CommandSender sender, String argument) {
        List<String> players = new ArrayList<>();

        for (Player online : Bukkit.getOnlinePlayers()) {
            String name = online.getName();

            if (StringUtils.startsWithIgnoreCase(name, argument) && VanishUtils.canSee(sender, online))
                players.add(name);
        }

        return players;
    }

    public static List<String> fromEnum(CommandSender sender, String argument, Enum[] array) {
        List<String> list = new ArrayList<>();

        for (Enum entry : array) {
            String name = entry.name().toLowerCase();

            if (StringUtils.startsWithIgnoreCase(name, argument))
                list.add(name);
        }

        return list;
    }

}
