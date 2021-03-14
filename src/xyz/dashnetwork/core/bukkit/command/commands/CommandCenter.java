package xyz.dashnetwork.core.bukkit.command.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dashnetwork.core.bukkit.command.CoreCommand;
import xyz.dashnetwork.core.bukkit.utils.MessageUtils;
import xyz.dashnetwork.core.bukkit.utils.PermissionType;

import java.util.Collections;

public class CommandCenter extends CoreCommand {

    public CommandCenter() {
        super(false, PermissionType.ADMIN, "center");
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location location = player.getLocation().clone();

            location.setX(location.getBlockX() + 0.5);
            location.setZ(location.getBlockZ() + 0.5);

            if (!player.isOnGround())
                location.setY(Math.round(location.getY()));

            player.teleport(location);
        } else
            MessageUtils.playersOnly();
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
