package xyz.dashnetwork.core.bungee.command.commands;

import net.md_5.bungee.api.CommandSender;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.utils.*;
import xyz.dashnetwork.core.utils.StringUtils;
import xyz.dashnetwork.core.utils.TimeUtils;

import java.util.*;

public class CommandTempmute extends CoreCommand {

    public CommandTempmute() {
        super(true, PermissionType.STAFF, "tempmute", "mutetemp");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        int length = args.length;

        if (length <= 1) {
            MessageUtils.message(sender, "&6&l» &7/tempmute <player> <time> <reason>");
            return;
        }

        UUID target;
        String name;

        try {
            target = UUID.fromString(args[0]);
            name = NameUtils.getUsername(target);

            if (name == null) {
                Messages.noPlayerFound(sender);
                return;
            }
        } catch (IllegalArgumentException invalid) {
            target = NameUtils.getUUID(args[0]);

            if (target == null) {
                Messages.noPlayerFound(sender);
                return;
            }

            name = NameUtils.getUsername(target);
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

        PunishUtils.mute(offline, sender, duration, reason);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return CompletionUtils.players(sender, args[0]);
        return Collections.EMPTY_LIST;
    }

}
