package dashnetwork.core.bukkit.command.commands;

import dashnetwork.core.bukkit.command.CoreCommand;
import dashnetwork.core.bukkit.utils.MessageUtils;
import dashnetwork.core.bukkit.utils.PermissionType;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

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

                if (meta != null && meta instanceof BlockStateMeta) {
                    BlockState state = ((BlockStateMeta) meta).getBlockState();

                    if (state instanceof Chest) {
                        Chest chest = (Chest) state;

                        player.openInventory(chest.getInventory());

                        return;
                    }
                }
            }

            MessageUtils.message(sender, "&6&l» &7You must be holding a chest");
        } else
            MessageUtils.playersOnly();
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
