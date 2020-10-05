package dashnetwork.core.bukkit.command.commands;

import dashnetwork.core.bukkit.command.CoreCommand;
import dashnetwork.core.bukkit.utils.MessageUtils;
import dashnetwork.core.bukkit.utils.PermissionType;
import dashnetwork.core.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

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
