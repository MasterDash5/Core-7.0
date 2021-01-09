package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.utils.*;
import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.utils.StringUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandKick extends CoreCommand {

    public CommandKick() {
        super(true, PermissionType.STAFF, "kick");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        int length = args.length;

        if (length <= 0) {
            MessageUtils.message(sender, "&6&l» &7/kick <player> <reason>");
            return;
        }

        ProxiedPlayer target = SelectorUtils.getPlayer(sender, args[0]);

        if (target == null) {
            Messages.noPlayerFound(sender);
            return;
        }

        if (sender instanceof ProxiedPlayer) {
            User user = User.getUser((ProxiedPlayer) sender);

            if (!user.isAbove(User.getUser(target))) {
                MessageUtils.message(sender, "&6&l» &7You don't have permission to kick that player.");
                return;
            }
        }

        String reason = "no reason provided";

        if (length > 1) {
            List<String> list = new ArrayList<>(Arrays.asList(args));
            list.remove(0);

            reason = StringUtils.unsplit(list, ' ');
        }

        PunishUtils.kick(User.getUser(target), sender, reason);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return CompletionUtils.players(sender, args[0]);
        return Collections.EMPTY_LIST;
    }

}
