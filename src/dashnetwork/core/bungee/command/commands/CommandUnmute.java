package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.*;
import dashnetwork.core.utils.MessageBuilder;
import dashnetwork.core.utils.PlayerProfile;
import dashnetwork.core.utils.PunishData;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.HoverEvent;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class CommandUnmute extends CoreCommand {

    private Map<String, String> names = DataUtils.getNames();
    private Map<String, PunishData> mutes = DataUtils.getMutes();

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

        mutes.remove(uuid);

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
