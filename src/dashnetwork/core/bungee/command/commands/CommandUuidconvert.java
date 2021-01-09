package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.MessageUtils;
import dashnetwork.core.bungee.utils.PermissionType;
import dashnetwork.core.utils.MessageBuilder;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.UUID;

public class CommandUuidconvert extends CoreCommand {

    public CommandUuidconvert() {
        super(true, PermissionType.OWNER, "uuidconvert");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length > 0) {
            UUID uuid;

            try {
                uuid = UUID.fromString(args[0]);
            } catch (IllegalArgumentException exception) {
                MessageUtils.message(sender, "&6&l» &cNot a valid UUID");
                return;
            }

            long most = uuid.getMostSignificantBits();
            long least = uuid.getLeastSignificantBits();
            byte[] bytes = ByteBuffer.allocate(16).putLong(most).putLong(least).array();
            int[] ints = new int[4];

            for (int i = 0; i < 4; i++)
                ints[i] = ByteBuffer.wrap(bytes, i * 4, 4).getInt();

            // Probably could've done better here
            String mostAndLeast = most + ", " + least;
            String array = ints[0] + ", " + ints[1] + ", " + ints[2] + ", " + ints[3];

            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» &7Most: &6" + most + "L, &7Least: &6" + least + "L")
                    .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, mostAndLeast)
                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, "&7Click to copy &6" + mostAndLeast);
            message.append("\n&6&l» &7Array: &6[I;" + array + "]")
                    .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, array)
                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, "&7Click to copy &6" + array);

            sender.sendMessage(message.build());
        } else
            MessageUtils.message(sender, "&6&l» &7/uuidconvert <uuid>");
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
