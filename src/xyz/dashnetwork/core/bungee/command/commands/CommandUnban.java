package xyz.dashnetwork.core.bungee.command.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.HoverEvent;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.utils.*;
import xyz.dashnetwork.core.utils.MessageBuilder;
import xyz.dashnetwork.core.utils.PlayerProfile;
import xyz.dashnetwork.core.utils.PunishData;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class CommandUnban extends CoreCommand {

    private Map<String, String> names = DataUtils.getNames();
    private Map<String, PunishData> bans = DataUtils.getBans();

    public CommandUnban() {
        super(true, PermissionType.ADMIN, "unban", "pardon");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        int length = args.length;

        if (length <= 0) {
            MessageUtils.message(sender, "&6&l» &7/unban <player>");
            return;
        }

        String name = null;
        String uuid = null;

        try {
            uuid = UUID.fromString(args[0]).toString();
        } catch (IllegalArgumentException exception) {
            for (Map.Entry<String, String> entry : names.entrySet()) {
                String value = entry.getValue();

                if (value.equalsIgnoreCase(args[0])) {
                    name = value;
                    uuid = entry.getKey();
                    break;
                }
            }
        }

        if (uuid == null) {
            PlayerProfile profile = MojangUtils.getUuidFromName(args[0]);

            if (profile == null) {
                Messages.noPlayerFound(sender);
                return;
            }

            name = profile.getName();
            uuid = profile.getUuid().toString();
        }

        DataUtils.getBans().remove(uuid);

        MessageBuilder broadcast = new MessageBuilder();
        broadcast.append("&6&l» ");
        broadcast.append("&6" + NameUtils.getDisplayName(sender)).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + NameUtils.getName(sender));
        broadcast.append(" &7unbanned ");
        broadcast.append("&6" + name).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + uuid);

        MessageUtils.broadcast(PermissionType.NONE, broadcast.build());
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return NameUtils.fromUuids(bans.keySet());
        return Collections.EMPTY_LIST;
    }

}