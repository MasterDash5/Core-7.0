package dashnetwork.core.utils;

import net.md_5.bungee.api.ChatColor;

public class ColorUtils {

    public static String translate(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String strip(String string) {
        return ChatColor.stripColor(translate(string));
    }

    public static String filter(String string, boolean color, boolean bold, boolean italic, boolean strikethrough, boolean underline, boolean magic) {
        String colors = "0123456789abcdefABCDEF";
        String bolds = "lL";
        String italics = "oO";
        String strikethroughs = "mM";
        String underlines = "nN";
        String magics = "kK";

        if (!color)
            for (char character : colors.toCharArray())
                string = string.replace("&" + character, "");

        if (!bold)
            for (char character : bolds.toCharArray())
                string = string.replace("&" + character, "");

        if (!italic)
            for (char character : italics.toCharArray())
                string = string.replace("&" + character, "");

        if (!strikethrough)
            for (char character : strikethroughs.toCharArray())
                string = string.replace("&" + character, "");

        if (!underline)
            for (char character : underlines.toCharArray())
                string = string.replace("&" + character, "");

        if (!magic)
            for (char character : magics.toCharArray())
                string = string.replace("&" + character, "");

        return string;
    }

}
