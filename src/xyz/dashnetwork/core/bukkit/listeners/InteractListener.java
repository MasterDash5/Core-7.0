package xyz.dashnetwork.core.bukkit.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dashnetwork.core.bukkit.Core;
import xyz.dashnetwork.core.bukkit.utils.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class InteractListener implements Listener {

    private static List<String> delay = new ArrayList<>();

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        User user = User.getUser(player);
        String name = player.getName();
        int paintbrush = user.getPaintbrush();

        if (paintbrush > 0 && player.getGameMode().equals(GameMode.CREATIVE)) {
            if (action.equals(Action.LEFT_CLICK_BLOCK)) {
                delay.add(name);

                new BukkitRunnable() {
                    public void run() {
                        delay.remove(name);
                    }
                }.runTaskLater(Core.getInstance(), 2);
            }

            if (action.name().contains("AIR")) {
                List<Block> blocks = player.getLastTwoTargetBlocks((Set<Material>) null, paintbrush);
                ItemStack item = player.getItemInHand();

                Block target = blocks.get(1);

                if (!delay.contains(name) && action.equals(Action.LEFT_CLICK_AIR))
                    target.setType(Material.AIR);
                else if (action.equals(Action.RIGHT_CLICK_AIR) && item != null && !target.getType().equals(Material.AIR)) {
                    Material material = item.getType();

                    if (material.isBlock()) {
                        Block block = blocks.get(0);

                        block.setType(material);
                        block.setData(item.getData().getData()); // fuck your deprecated methods
                    }
                }
            }
        }
    }

}
