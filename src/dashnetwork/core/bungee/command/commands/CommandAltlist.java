package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.NameUtils;
import dashnetwork.core.bungee.utils.PermissionType;
import net.md_5.bungee.api.CommandSender;

public class CommandAltlist extends CoreCommand {

    public CommandAltlist() {
        super(true, PermissionType.STAFF, "altlist", "alts");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        // TODO
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return NameUtils.toNames(bungee.getPlayers());
    }

}
