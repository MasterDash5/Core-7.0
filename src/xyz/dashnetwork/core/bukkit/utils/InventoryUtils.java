package xyz.dashnetwork.core.bukkit.utils;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {

    public static void fillRemaining(Inventory inventory, ItemStack item) {
        for (int i = 0; i < inventory.getSize(); i++)
            if (inventory.getItem(i) == null)
                inventory.setItem(i, item);
    }

}
