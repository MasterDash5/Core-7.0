package xyz.dashnetwork.core.bukkit.utils;

import org.bukkit.DyeColor;

import java.util.Random;

public class RandomUtils {

    private static Random random = new Random();

    public static DyeColor randomDyeColor() {
        DyeColor[] colors = DyeColor.values();

        return colors[random.nextInt(colors.length - 1)];
    }

}
