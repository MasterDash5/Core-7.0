package xyz.dashnetwork.core.utils;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;

import java.util.List;

public class PageUtils {

    public static BaseComponent[] createPagedMessage(int page, List<BaseComponent[]> message) {
        MessageBuilder builder = new MessageBuilder();

        int length = message.size();
        int end = page * 5;
        int start = end - 5;
        int total = (int) Math.ceil(length / (page * 5)); // Why does Math.ceil return a double

        builder.append("&6&l» &7Page &6" + page + "&7/&6" + total).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + length + " total entries");

        for (int i = start; i < end && i < length; i++)
            builder.append(message.get(i));

        return builder.build();
    }

    public static BaseComponent[] createPagedMessage(int page, String message) {
        MessageBuilder builder = new MessageBuilder();
        String[] lines = message.split("\n");

        int length = lines.length;
        int end = page * 5;
        int start = end - 5;
        int total = (int) Math.ceil(length / (page * 5)); // Why does Math.ceil return a double

        builder.append("&6&l» &7Page &6" + page + "&7/&6" + total).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + length + " total entries");

        for (int i = start; i < end && i < length; i++)
            builder.append(lines[i]);

        return builder.build();
    }

}
