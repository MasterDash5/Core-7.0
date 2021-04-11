package xyz.dashnetwork.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static boolean containsIgnoreCase(String string, String contains) {
        return string.toLowerCase().contains(contains.toLowerCase());
    }

    public static String fromList(List<String> list, boolean useAnd, boolean formatDuplicates) {
        String string = "";

        if (formatDuplicates) {
            Map<String, Integer> duplicates = new HashMap<>();
            List<String> formatted = new ArrayList<>();

            for (String entry : list)
                duplicates.put(entry, duplicates.getOrDefault(entry, 0) + 1);

            for (Map.Entry<String, Integer> entry : duplicates.entrySet()) {
                int amount = entry.getValue();

                formatted.add(entry.getKey() + (amount > 1 ? " x" + amount : ""));
            }

            list = formatted;
        }

        for (int i = 0; i < list.size(); i++) {
            String entry = list.get(i);

            if (i == 0)
                string = entry;
            else if (list.size() == i + 1 && useAnd) {
                if (list.size() == 2)
                    string += " and " + entry;
                else
                    string += ", and " + entry;
            } else
                string += ", " + entry;
        }

        if (string.isEmpty())
            return "None";

        return string;
    }

}
