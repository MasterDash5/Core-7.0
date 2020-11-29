package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.MessageUtils;
import dashnetwork.core.bungee.utils.NameUtils;
import dashnetwork.core.bungee.utils.PermissionType;
import dashnetwork.core.utils.StringUtils;
import net.md_5.bungee.api.CommandSender;

import java.util.Collections;

public class CommandChat extends CoreCommand {

    public CommandChat() {
        super(true, PermissionType.OWNER, "chat");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length > 0) {
            String message = StringUtils.unsplit(args, ' ');

            MessageUtils.broadcast(PermissionType.NONE, "&6" + NameUtils.getDisplayName(sender) + " &e&l>&f " + message);
        } else
            MessageUtils.message(sender, "&6&l»&7 /chat <message>");
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
