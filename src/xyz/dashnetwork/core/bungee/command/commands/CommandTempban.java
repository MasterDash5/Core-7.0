package xyz.dashnetwork.core.bungee.command.commands;

import net.md_5.bungee.api.CommandSender;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.utils.*;
import xyz.dashnetwork.core.utils.*;

import java.util.*;

public class CommandTempban extends CoreCommand {

    public CommandTempban() {
        super(true, PermissionType.ADMIN, "tempban", "bantemp");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        int length = args.length;

        if (length <= 1) {
            MessageUtils.message(sender, "&6&l» &7/tempban <player> <time> <reason>");
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
                    Messages.noPlayerFound(sender);
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
                    Messages.noPlayerFound(sender);
                    return;
                }

                target = profile.getUuid();
                name = profile.getName();
            } else {
                target = UUID.fromString(fromName);
                name = names.get(target);
            }
        }

        Long input = TimeUtils.fromTimeArgument(args[1]);

        if (input == null) {
            MessageUtils.message(sender, "&6&l» &cInvalid time input");
            return;
        }

        long duration = System.currentTimeMillis() + input;
        String reason = "no reason provided";

        if (length > 2) {
            List<String> list = new ArrayList<>(Arrays.asList(args));
            list.remove(0);
            list.remove(0);

            reason = StringUtils.unsplit(list, ' ');
        }

        OfflineUser offline = OfflineUser.getOfflineUser(target, name);

        PunishUtils.ban(offline, sender, duration, reason);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return CompletionUtils.players(sender, args[0]);
        return Collections.EMPTY_LIST;
    }

}
