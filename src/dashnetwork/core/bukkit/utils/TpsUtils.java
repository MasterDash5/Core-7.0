package dashnetwork.core.bukkit.utils;

import dashnetwork.core.bukkit.Core;
import org.bukkit.scheduler.BukkitRunnable;

public class TpsUtils {

    private static int count = 0;
    private static long[] ticks = new long[600];

    public static void startup() {
        new BukkitRunnable() {
            public void run() {
                ticks[(count % ticks.length)] = System.currentTimeMillis();
                count++;
            }
        }.runTaskTimer(Core.getInstance(), 0, 1);
    }

    public static double getTPS() {
        if (count < 100)
            return -1;

        try {
            int target = (count - 101) % ticks.length;
            long elapsed = System.currentTimeMillis() - ticks[target];

            return 100D / (elapsed / 1000D);
        } catch (Exception exception) {
            return -1;
        }
    }

}
