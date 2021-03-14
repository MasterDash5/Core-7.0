package xyz.dashnetwork.core.bukkit.command.commands;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dashnetwork.core.bukkit.command.CoreCommand;
import xyz.dashnetwork.core.bukkit.utils.MessageUtils;
import xyz.dashnetwork.core.bukkit.utils.PermissionType;

import java.util.Collections;

public class CommandAnvil extends CoreCommand {

    public CommandAnvil() {
        super(false, PermissionType.OWNER, "anvil");
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Block feet = player.getLocation().getBlock();
            Block head = player.getEyeLocation().getBlock();
            Block target = null;

            if (feet.getType().equals(Material.AIR))
                target = feet;
            else if (head.getType().equals(Material.AIR))
                target = head;

            if (target == null)
                MessageUtils.message(sender, "&6&l» &7You must be standing inside an air block");
            else
                target.setType(Material.ANVIL);
        } else
            MessageUtils.playersOnly();
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
