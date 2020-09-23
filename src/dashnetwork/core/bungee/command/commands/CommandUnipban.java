package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.DataUtils;
import dashnetwork.core.bungee.utils.MessageUtils;
import dashnetwork.core.bungee.utils.NameUtils;
import dashnetwork.core.bungee.utils.PermissionType;
import dashnetwork.core.utils.ColorUtils;
import dashnetwork.core.utils.MessageBuilder;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.HoverEvent;

import java.util.Collections;

public class CommandUnipban extends CoreCommand {

    public CommandUnipban() {
        super(true, PermissionType.OWNER, "unipban", "unbanip", "pardonip", "ippardon");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        int length = args.length;

        if (length <= 0) {
            MessageUtils.message(sender, ColorUtils.translate("&6&l» &7/unban <player>"));
            return;
        }

        String address = args[0];

        DataUtils.getBans().remove(address);

        MessageBuilder broadcast = new MessageBuilder();
        broadcast.append("&6&l» ");
        broadcast.append("&6" + NameUtils.getDisplayName(sender)).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + NameUtils.getName(sender));
        broadcast.append(" &7unbanned &6" + address);

        MessageUtils.broadcast(PermissionType.ADMIN, broadcast.build());
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return NameUtils.fromUuids(DataUtils.getIpbans().keySet());
        return Collections.EMPTY_LIST;
    }

}
