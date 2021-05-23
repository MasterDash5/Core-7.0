package xyz.dashnetwork.core.bukkit.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.dashnetwork.core.bukkit.command.CoreCommand;
import xyz.dashnetwork.core.bukkit.utils.MessageUtils;
import xyz.dashnetwork.core.bukkit.utils.PermissionType;

import java.util.Collections;
import java.util.Set;

public class CommandPeek extends CoreCommand {

    public CommandPeek() {
        super(false, PermissionType.ADMIN, "peek");
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ItemStack item = player.getItemInHand();

            if (item != null) {
                ItemMeta meta = item.getItemMeta();

                if (meta instanceof BlockStateMeta) {
                    BlockState state = ((BlockStateMeta) meta).getBlockState();

                    if (state instanceof Chest) {
                        Inventory inventory = ((InventoryHolder) state).getInventory();
                        int size = inventory.getSize();

                        Inventory created = Bukkit.createInventory(null, size);

                        for (int i = 0; i < size; i++)
                            created.setItem(i, inventory.getItem(i));

                        player.openInventory(created);
                        return;
                    }
                }
            }

            MessageUtils.message(sender, "&6&l» &cYou must be holding a chest");
        } else
            MessageUtils.playersOnly();
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
