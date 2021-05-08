package xyz.dashnetwork.core.bukkit.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueueUtils {

    private static Map<String, String> displaynameQueue = new HashMap<>();
    private static List<String> vanishQueue = new ArrayList<>();
    private static List<String> signspyQueue = new ArrayList<>();
    private static List<String> bedrockQueue = new ArrayList<>();

    public static Map<String, String> getDisplaynameQueue() {
        return displaynameQueue;
    }

    public static List<String> getVanishQueue() {
        return vanishQueue;
    }

    public static List<String> getSignspyQueue() {
        return signspyQueue;
    }

    public static List<String> getBedrockQueue() {
        return bedrockQueue;
    }

}
