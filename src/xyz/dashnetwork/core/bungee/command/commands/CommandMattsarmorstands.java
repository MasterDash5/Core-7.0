package xyz.dashnetwork.core.bungee.command.commands;

import net.md_5.bungee.api.CommandSender;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.utils.MessageUtils;
import xyz.dashnetwork.core.bungee.utils.PermissionType;

import java.util.Collections;

public class CommandMattsarmorstands extends CoreCommand {

    public CommandMattsarmorstands() {
        super(true, PermissionType.NONE, "mattsarmorstands");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        MessageUtils.message(sender, "&c&lMattsArmorStands &6&l>> &6Developed by MM5. Version &cv1.0");
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
