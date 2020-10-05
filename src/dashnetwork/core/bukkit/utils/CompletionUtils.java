package dashnetwork.core.bukkit.utils;

import dashnetwork.core.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CompletionUtils {

    public static List<String> players(String argument) {
        List<String> players = new ArrayList<>();

        for (Player online : Bukkit.getOnlinePlayers()) {
            String name = online.getName();

            if (StringUtils.startsWithIgnoreCase(name, argument))
                players.add(name);
        }

        return players;
    }

}
