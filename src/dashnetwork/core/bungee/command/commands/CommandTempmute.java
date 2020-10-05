package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.*;
import dashnetwork.core.utils.ColorUtils;
import dashnetwork.core.utils.StringUtils;
import dashnetwork.core.utils.TimeUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

        ProxiedPlayer target = SelectorUtils.getPlayer(sender, args[0]);

        if (target == null) {
            MessageUtils.noPlayerFound(sender);
            return;
        }

        if (sender instanceof ProxiedPlayer) {
            User user = User.getUser((ProxiedPlayer) sender);

            if (!user.isAbove(User.getUser(target))) {
                MessageUtils.message(sender, "&6&l» &7You don't have permission to mute that player.");
                return;
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

        PunishUtils.mute(User.getUser(target), duration, sender, reason);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return CompletionUtils.players(args[0]);
        return Collections.EMPTY_LIST;
    }

}
