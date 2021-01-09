package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.MessageUtils;
import dashnetwork.core.bungee.utils.PermissionType;
import net.md_5.bungee.api.CommandSender;

import java.util.Collections;

public class CommandSkyblock extends CoreCommand {

    public CommandSkyblock() {
        super(true, PermissionType.NONE, "skyblock");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        MessageUtils.message(sender, "&6&l»&c This server is under maintenance");
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
