package xyz.dashnetwork.core.utils;

import net.md_5.bungee.api.ChatColor;

import java.awt.*;

public class ColorUtils {

    public static String translate(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String strip(String string) {
        return ChatColor.stripColor(translate(string));
    }

    public static String hex(String string) {
        int length = string.length();

        for (int i = 0; i < length; i++) {
            if (length - (i + 7) > 0 && string.charAt(i) == '&') {
                String hex = string.substring(i + 1, i + 8);

                if (hex.matches("#-?[0-9a-fA-F]+")) {
                    Color color = new Color(
                            Integer.valueOf(hex.substring(1, 3), 16),
                            Integer.valueOf(hex.substring(3, 5), 16),
                            Integer.valueOf(hex.substring(5, 7), 16));

                    String format = String.format("%06x", color.getRGB() & 0xFFFFFF);
                    String output = "§x";

                    for (char character : format.toCharArray())
                        output += "§" + character;

                    string = string.replace("&" + hex, output);
                }
            }
        }

        return string;
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
