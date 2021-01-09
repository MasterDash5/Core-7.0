package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.CompletionUtils;
import dashnetwork.core.bungee.utils.MessageUtils;
import dashnetwork.core.bungee.utils.PermissionType;
import dashnetwork.core.utils.MessageBuilder;
import dashnetwork.core.utils.StringUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.HoverEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandFakejoin extends CoreCommand {

    public CommandFakejoin() {
        super(true, PermissionType.ADMIN, "fakejoin");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length <= 1) {
            MessageUtils.message(sender, "&6&l» &7/fakejoin <username> <displayname>");
            return;
        }

        List<String> list = new ArrayList<>(Arrays.asList(args));
        list.remove(0);

        String username = args[0];
        String displayname = StringUtils.unsplit(list, ' ');

        MessageBuilder message = new MessageBuilder();
        message.append("&a&l» ");
        message.append("&6" + displayname).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + username);
        message.append("&a joined the server.");

        MessageUtils.broadcast(PermissionType.NONE, message.build());
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return CompletionUtils.players(sender, args[0]);
        return Collections.EMPTY_LIST;
    }

}
