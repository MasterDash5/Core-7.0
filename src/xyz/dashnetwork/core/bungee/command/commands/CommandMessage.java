package xyz.dashnetwork.core.bungee.command.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.utils.*;
import xyz.dashnetwork.core.utils.ColorUtils;
import xyz.dashnetwork.core.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandMessage extends CoreCommand {

    public CommandMessage() {
        super(true, PermissionType.NONE, "message", "msg", "tell", "whisper", "w");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            if (args.length <= 1) {
                MessageUtils.message(sender, "&6&l» &7/message <player> <message>");
                return;
            }

            ProxiedPlayer player = (ProxiedPlayer) sender;
            User playerUser = User.getUser(player);

            if (playerUser.isMuted()) {
                Messages.muted(sender, DataUtils.getMutes().get(player.getUniqueId().toString()));
                return;
            }

            ProxiedPlayer target = SelectorUtils.getPlayer(sender, args[0]);

            if (target == null) {
                Messages.noPlayerFound(sender);
                return;
            }

            User targetUser = User.getUser(target);
            String targetReply = targetUser.getReplyTarget();

            if (!VanishUtils.canSee(player, target)) {
                Messages.noPlayerFound(sender);
                return;
            }

            playerUser.setReplyTarget(target.getUniqueId().toString());

            if (targetReply == null || bungee.getPlayer(targetReply) == null)
                targetUser.setReplyTarget(player.getUniqueId().toString());

            List<String> argList = new ArrayList<>(Arrays.asList(args));
            argList.remove(0);

            String message = StringUtils.unsplit(argList, ' ');

            if (!playerUser.isStaff())
                message = ColorUtils.filter(message, true, true, true, true, false, false);

            playerUser.privateMessage(targetUser, message);
        } else
            Messages.playersOnly();
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return CompletionUtils.players(sender, args[0]);
        return Collections.EMPTY_LIST;
    }

}
