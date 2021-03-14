package xyz.dashnetwork.core.bungee.command.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.utils.*;
import xyz.dashnetwork.core.utils.ColorUtils;
import xyz.dashnetwork.core.utils.StringUtils;

import java.util.Collections;
import java.util.UUID;

public class CommandReply extends CoreCommand {

    public CommandReply() {
        super(true, PermissionType.NONE, "reply", "r");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            if (args.length < 1) {
                MessageUtils.message(sender, "&6&l» &7/reply <message>");
                return;
            }

            ProxiedPlayer player = (ProxiedPlayer) sender;
            User playerUser = User.getUser(player);

            if (playerUser.isMuted()) {
                Messages.muted(sender, DataUtils.getMutes().get(player.getUniqueId().toString()));
                return;
            }

            String reply = playerUser.getReplyTarget();

            if (reply == null) {
                MessageUtils.message(sender, "&6&l» &cYou haven't messaged anyone recently");
                return;
            }

            ProxiedPlayer target = bungee.getPlayer(UUID.fromString(reply));
            User targetUser = User.getUser(target);

            if (!VanishUtils.canSee(player, target)) {
                Messages.noPlayerFound(sender);
                return;
            }

            String message = StringUtils.unsplit(args, ' ');

            if (!playerUser.isStaff())
                message = ColorUtils.filter(message, true, true, true, true, false, false);

            playerUser.privateMessage(targetUser, message);
        } else
            Messages.playersOnly();
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
