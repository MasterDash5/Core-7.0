package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.*;
import dashnetwork.core.utils.StringUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

        String reason = "no reason provided";

        if (length > 1) {
            List<String> list = new ArrayList<>(Arrays.asList(args));
            list.remove(0);

            reason = StringUtils.unsplit(list, ' ');
        }

        PunishUtils.mute(User.getUser(target), null, sender, reason);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sendr, String[] args) {
        if (args.length == 1)
            return NameUtils.toNames(bungee.getPlayers());
        return Collections.EMPTY_LIST;
    }

}
