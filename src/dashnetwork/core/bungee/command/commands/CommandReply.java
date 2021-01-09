package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.*;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
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

            String reply = playerUser.getReplyTarget();

            if (reply == null) {
                MessageUtils.message(sender, "&6&l» &cYou haven't messaged anyone recently");
                return;
            }

            ProxiedPlayer target = bungee.getPlayer(UUID.fromString(reply));
            User targetUser = User.getUser(target);

            if (!VanishUtils.canSee(player, target)) {
                MessageUtils.noPlayerFound(sender);
                return;
            }

            String message = StringUtils.unsplit(args, ' ');

            if (!playerUser.isStaff())
                message = ColorUtils.filter(message, true, true, true, true, false, false);

            playerUser.privateMessage(targetUser, message);
        } else
            MessageUtils.playersOnly();
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
