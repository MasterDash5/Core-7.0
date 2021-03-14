package xyz.dashnetwork.core.utils;

import java.util.Map;

public class MapUtils {

    public static <K, V> K getKeyFromValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet())
            if (entry.getValue().equals(value))
                return entry.getKey();
        return null;
    }

}