package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.*;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.CommandSender;

import java.util.*;

public class CommandBan extends CoreCommand {

    public CommandBan() {
        super(true, PermissionType.ADMIN, "ban");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        int length = args.length;

        if (length <= 0) {
            MessageUtils.message(sender, "&6&l» &7/ban <player> <reason>");
            return;
        }

        Map<String, String> names = DataUtils.getNames();

        UUID target;
        String name;

        try {
            target = UUID.fromString(args[0]);
            name = names.get(target);

            if (name == null) {
                MessageUtils.message(sender, "&6&l» &7Unable to find player locally. Looking up from Mojang...");

                Username[] usernames = MojangUtils.getNameHistoryFromUuid(target);

                if (usernames == null) {
                    MessageUtils.noPlayerFound(sender);
                    return;
                }

                name = usernames[usernames.length - 1].getName();
            }
        } catch (IllegalArgumentException invalid) {
            String fromName = MapUtils.getKeyFromValue(names, args[0]);

            if (fromName == null) {
                MessageUtils.message(sender, "&6&l» &7Unable to find player locally. Looking up from Mojang...");

                PlayerProfile profile = MojangUtils.getUuidFromName(args[0]);

                if (profile == null) {
                    MessageUtils.noPlayerFound(sender);
                    return;
                }

                target = profile.getUuid();
                name = profile.getName();
            } else {
                target = UUID.fromString(fromName);
                name = names.get(target);
            }
        }

        String reason = "no reason provided";

        if (length > 1) {
            List<String> list = new ArrayList<>(Arrays.asList(args));
            list.remove(0);

            reason = StringUtils.unsplit(list, ' ');
        }

        PunishUtils.ban(target, name, null, sender, reason);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return CompletionUtils.players(args[0]);
        return Collections.EMPTY_LIST;
    }

}
