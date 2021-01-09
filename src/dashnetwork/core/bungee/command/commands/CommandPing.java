package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.*;
import dashnetwork.core.utils.MessageBuilder;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collections;

public class CommandPing extends CoreCommand {

    public CommandPing() {
        super(true, PermissionType.NONE, "ping");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        ProxiedPlayer target = null;

        if (args.length > 0)
            target = SelectorUtils.getPlayer(sender, args[0]);
        else if (sender instanceof ProxiedPlayer)
            target = (ProxiedPlayer) sender;

        if (target == null || !VanishUtils.canSee(sender, target)) {
            Messages.noPlayerFound(sender);
            return;
        }

        MessageBuilder message = new MessageBuilder();
        message.append("&6&l» ");
        message.append("&6" + User.getUser(target).getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + target.getName());
        message.append(" &7ping: &6" + target.getPing() + "ms");

        MessageUtils.message(sender, message.build());
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return CompletionUtils.players(sender, args[0]);
        return Collections.EMPTY_LIST;
    }

}
