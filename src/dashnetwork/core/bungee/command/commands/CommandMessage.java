package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.*;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.text.SimpleDateFormat;
import java.util.*;

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
                PunishData data = DataUtils.getMutes().get(player.getUniqueId().toString());
                Long expire = data.getExpire();
                String date = expire == null ? "never" : new SimpleDateFormat("MMM d, hh:mm a z").format(new Date(expire));

                MessageBuilder reponse = new MessageBuilder();
                reponse.append("&6&l» &7You are muted. &6Hover for details")
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT,
                                "&6Muted by &7" + data.getBanner()
                                        + "\n&6Expires &7" + date
                                        + "\n&6Reason: &7" + data.getReason());

                MessageUtils.message(player, reponse.build());

                return;
            }

            ProxiedPlayer target = SelectorUtils.getPlayer(sender, args[0]);

            if (target == null) {
                MessageUtils.noPlayerFound(sender);
                return;
            }

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

            MessageBuilder socialSpy = new MessageBuilder();
            socialSpy.append("&c&lSS ");
            socialSpy.append("&6" + playerDisplayName).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + playerName);
            socialSpy.append("&a -> ");
            socialSpy.append("&6" + targetDisplayName).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + targetName);
            socialSpy.append(" &6&l> &7" + message);

            for (User user : User.getUsers(true))
                if (user.isStaff() && !user.inCommandSpy() && !LazyUtils.anyEquals(user, playerUser, targetUser))
                    MessageUtils.message(user, socialSpy.build());

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
