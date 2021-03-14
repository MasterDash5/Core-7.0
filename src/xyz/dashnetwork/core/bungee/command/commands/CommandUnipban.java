package xyz.dashnetwork.core.bungee.command.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.HoverEvent;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.utils.DataUtils;
import xyz.dashnetwork.core.bungee.utils.MessageUtils;
import xyz.dashnetwork.core.bungee.utils.NameUtils;
import xyz.dashnetwork.core.bungee.utils.PermissionType;
import xyz.dashnetwork.core.utils.MessageBuilder;

import java.util.Collections;

public class CommandUnipban extends CoreCommand {

    public CommandUnipban() {
        super(true, PermissionType.OWNER, "unipban", "unbanip", "pardonip", "ippardon");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        int length = args.length;

        if (length <= 0) {
            MessageUtils.message(sender, "&6&l» &7/unipban <player>");
            return;
        }

        String address = args[0];

        DataUtils.getIpbans().remove(address);

        MessageBuilder broadcast = new MessageBuilder();
        broadcast.append("&6&l» ");
        broadcast.append("&6" + NameUtils.getDisplayName(sender)).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + NameUtils.getName(sender));
        broadcast.append(" &7unipbanned &6" + address);

        MessageUtils.broadcast(PermissionType.ADMIN, broadcast.build());
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
