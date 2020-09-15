package dashnetwork.core.utils;

import net.md_5.bungee.api.ChatColor;

public class ColorUtils {

    public static String translate(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}
