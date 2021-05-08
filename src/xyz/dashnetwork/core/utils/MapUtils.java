package xyz.dashnetwork.core.utils;

import java.util.Map;

public class MapUtils {

    public static <K, V> K getKeyFromValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet())
            if (entry.getValue().equals(value))
                return entry.getKey();
        return null;
    }

    public static <K, V> K getKeyFromValueIgnoreCase(Map<K, String> map, String value) {
        for (Map.Entry<K, String> entry : map.entrySet())
            if (entry.getValue().equalsIgnoreCase(value))
                return entry.getKey();
        return null;
    }

}