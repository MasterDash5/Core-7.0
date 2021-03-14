package xyz.dashnetwork.core.bungee.command.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.events.UserChatEvent;
import xyz.dashnetwork.core.bungee.utils.MessageUtils;
import xyz.dashnetwork.core.bungee.utils.NameUtils;
import xyz.dashnetwork.core.bungee.utils.PermissionType;
import xyz.dashnetwork.core.bungee.utils.User;
import xyz.dashnetwork.core.utils.Channel;
import xyz.dashnetwork.core.utils.StringUtils;

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

            if (sender instanceof ProxiedPlayer) {
                User user = User.getUser((ProxiedPlayer) sender);

                bungee.getPluginManager().callEvent(new UserChatEvent(user, Channel.GLOBAL, message));
            }
        } else
            MessageUtils.message(sender, "&6&l»&7 /chat <message>");
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
