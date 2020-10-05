package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.*;
import dashnetwork.core.utils.ColorUtils;
import dashnetwork.core.utils.MessageBuilder;
import dashnetwork.core.utils.StringUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandMsg extends CoreCommand {

    public CommandMsg() {
        super(true, PermissionType.NONE, "msg", "message", "tell", "whisper", "w");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            if (args.length <= 1) {
                MessageUtils.message(sender, "&6&l» &7/msg <player> <message>");
                return;
            }

            ProxiedPlayer player = (ProxiedPlayer) sender;
            ProxiedPlayer target = SelectorUtils.getPlayer(sender, args[0]);

            if (target == null) {
                MessageUtils.noPlayerFound(sender);
                return;
            }

            User playerUser = User.getUser(player);
            User targetUser = User.getUser(target);
            String targetReply = targetUser.getReplyTarget();

            if (targetUser.isVanished() && !playerUser.isStaff()) {
                MessageUtils.noPlayerFound(sender);
                return;
            }

            playerUser.setReplyTarget(target.getUniqueId().toString());

            if (targetReply == null || bungee.getPlayer(targetReply) == null)
                targetUser.setReplyTarget(player.getUniqueId().toString());

            List<String> argList = new ArrayList<>(Arrays.asList(args));
            argList.remove(0);

            String message = StringUtils.unsplit(argList, ' ');
            String playerName = player.getName();
            String targetName = target.getName();
            String playerDisplayName = playerUser.getDisplayName();
            String targetDisplayName = targetUser.getDisplayName();

            if (!playerUser.isStaff())
                message = ColorUtils.filter(message, true, true, true, true, false, false);

            MessageBuilder toPlayer = new MessageBuilder();
            toPlayer.append("&6&l» &aMe -> ");
            toPlayer.append("&6" + targetDisplayName).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + targetName);
            toPlayer.append(" &6&l> &7" + message);

            MessageUtils.message(player, toPlayer.build());

            MessageBuilder toTarget = new MessageBuilder();
            toTarget.append("&6&l» ");
            toTarget.append("&6" + playerDisplayName).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + playerName);
            toTarget.append("&a -> Me &6&l> &7" + message);

            MessageUtils.message(target, toTarget.build());

            MessageBuilder socialSpy = new MessageBuilder();
            socialSpy.append("&c&lSS ");
            socialSpy.append("&6" + playerDisplayName).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + playerName);
            socialSpy.append("&a -> ");
            socialSpy.append("&6" + targetDisplayName).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + targetName);
            socialSpy.append(" &6&l> &7" + message);

            for (User user : User.getUsers(true))
                if (user.isStaff() && !user.inCommandSpy())
                    MessageUtils.message(user, socialSpy.build());
        } else
            MessageUtils.playersOnly();
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return CompletionUtils.players(args[0]);
        return Collections.EMPTY_LIST;
    }

}
