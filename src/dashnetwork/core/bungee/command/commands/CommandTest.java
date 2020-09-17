package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.MessageUtils;
import dashnetwork.core.bungee.utils.PermissionType;
import net.md_5.bungee.api.CommandSender;

import java.util.Arrays;

public class CommandTest extends CoreCommand {

    public CommandTest() {
        super(true, PermissionType.OWNER, "test");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        MessageUtils.message(sender, "Testy");
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Arrays.asList("completion_test");
    }

}
