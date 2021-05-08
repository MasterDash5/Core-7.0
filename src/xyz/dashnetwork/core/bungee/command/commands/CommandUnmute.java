package xyz.dashnetwork.core.bungee.command.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.HoverEvent;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.utils.*;
import xyz.dashnetwork.core.utils.MessageBuilder;
import xyz.dashnetwork.core.utils.PunishData;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class CommandUnmute extends CoreCommand {

    private static Map<String, PunishData> mutes = DataUtils.getMutes();

    public CommandUnmute() {
        super(true, PermissionType.STAFF, "unmute");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        int length = args.length;

        if (length <= 0) {
            MessageUtils.message(sender, "&6&l» &7/unmute <player>");
            return;
        }

        UUID uuid;
        String name;

        try {
            uuid = UUID.fromString(args[0]);
            name = NameUtils.getUsername(uuid);

            if (name == null) {
                Messages.noPlayerFound(sender);
                return;
            }
        } catch (IllegalArgumentException invalid) {
            uuid = NameUtils.getUUID(args[0]);

            if (uuid == null) {
                Messages.noPlayerFound(sender);
                return;
            }

            name = NameUtils.getUsername(uuid);
        }

        mutes.remove(uuid.toString());

        MessageBuilder broadcast = new MessageBuilder();
        broadcast.append("&6&l» ");
        broadcast.append("&6" + NameUtils.getDisplayName(sender)).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + NameUtils.getName(sender));
        broadcast.append(" &7unmuted ");
        broadcast.append("&6" + name).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + uuid);

        MessageUtils.broadcast(PermissionType.NONE, broadcast.build());
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return NameUtils.fromUuids(mutes.keySet());
        return Collections.EMPTY_LIST;
    }

}
