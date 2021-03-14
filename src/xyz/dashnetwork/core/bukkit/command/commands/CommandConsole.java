package xyz.dashnetwork.core.bukkit.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import xyz.dashnetwork.core.bukkit.command.CoreCommand;
import xyz.dashnetwork.core.bukkit.utils.MessageUtils;
import xyz.dashnetwork.core.bukkit.utils.PermissionType;
import xyz.dashnetwork.core.utils.StringUtils;

import java.util.Collections;

public class CommandConsole extends CoreCommand {

    public CommandConsole() {
        super(false, PermissionType.OWNER, "console");
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (args.length > 0)
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), StringUtils.unsplit(args, ' '));
        else
            MessageUtils.message(sender, "&6&l» &7/console <command>");
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
