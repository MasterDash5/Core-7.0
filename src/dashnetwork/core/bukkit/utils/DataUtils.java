package dashnetwork.core.bukkit.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataUtils {

    private static Map<String, String> displaynameQueue = new HashMap<>();
    private static List<String> vanishQueue = new ArrayList<>();

    public static Map<String, String> getDisplaynameQueue() {
        return displaynameQueue;
    }

    public static List<String> getVanishQueue() {
        return vanishQueue;
    }

}
