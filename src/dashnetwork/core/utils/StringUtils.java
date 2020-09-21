package dashnetwork.core.utils;

import java.util.List;

public class StringUtils {

    public static String unsplit(String[] strings, char split) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < strings.length; i++) {
            if (i > 0)
                builder.append(split);
            builder.append(strings[i]);
        }

        return builder.toString();
    }

    public static String unsplit(List<String> strings, char split) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < strings.size(); i++) {
            if (i > 0)
                builder.append(split);
            builder.append(strings.get(i));
        }

        return builder.toString();
    }

    public static String shortenNumber(Number number, int places) {
        String string = number.toString();
        String[] split = string.split("\\.");

        if (split.length != 2 || split[1].length() < places)
            return string;

        StringBuilder shortened = new StringBuilder(split[0] + ".");

        for (int i = 0; i < places; i++)
            shortened.append(split[1].charAt(i));

        return shortened.toString();
    }

    public static String clear(String string, String clear) {
        while (string.contains(clear))
            string = string.replace(clear, "");
        return string;
    }

    public static boolean startsWithIgnoreCase(String string, String starts) {
        return string.toLowerCase().startsWith(starts.toLowerCase());
    }

    public static boolean endsWithIgnoreCase(String string, String ends) {
        return string.toLowerCase().endsWith(ends.toLowerCase());
    }

}
