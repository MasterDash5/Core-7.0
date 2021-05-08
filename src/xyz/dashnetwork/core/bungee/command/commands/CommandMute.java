package xyz.dashnetwork.core.bungee.command.commands;

import net.md_5.bungee.api.CommandSender;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.utils.*;
import xyz.dashnetwork.core.utils.StringUtils;

import java.util.*;

public class CommandMute extends CoreCommand {

    public CommandMute() {
        super(true, PermissionType.STAFF, "mute");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        int length = args.length;

        if (length <= 0) {
            MessageUtils.message(sender, "&6&l» &7/mute <player> <reason>");
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

        String reason = "no reason provided";

        if (length > 1) {
            List<String> list = new ArrayList<>(Arrays.asList(args));
            list.remove(0);

            reason = StringUtils.unsplit(list, ' ');
        }

        OfflineUser offline = OfflineUser.getOfflineUser(target, name);

        PunishUtils.mute(offline, sender, null, reason);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return CompletionUtils.players(sender, args[0]);
        return Collections.EMPTY_LIST;
    }

}
