package xyz.dashnetwork.core.bungee.command.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.utils.*;
import xyz.dashnetwork.core.utils.MessageBuilder;
import xyz.dashnetwork.core.utils.PunishData;
import xyz.dashnetwork.core.utils.TimeUtils;

import java.util.Collections;
import java.util.Date;
import java.util.UUID;

public class CommandSeen extends CoreCommand {

    public CommandSeen() {
        super(true, PermissionType.NONE, "seen");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length < 1) {
            MessageUtils.message(sender, "&6&l» &7/seen <player>");
            return;
        }

        UUID uuid = NameUtils.getUUID(args[0]);

        if (uuid == null) {
            Messages.noPlayerFound(sender);
            return;
        }

        String username = NameUtils.getUsername(uuid);
        OfflineUser offline = OfflineUser.getOfflineUser(uuid, username);

        boolean online = offline.isOnline();
        long seen = offline.getLastPlayed();
        String stringUuid = uuid.toString();
        String nickname = offline.getNickname();
        String address = offline.getAddress();
        String status = online ? "&aonline" : "&coffline";

        MessageBuilder message = new MessageBuilder();
        message.append("&6&l» &6" + offline.getName() + " &7is " + status);

        if (seen > 0) {
            long time = System.currentTimeMillis() - seen;

            message.append("\n&7 - Last seen &6" + TimeUtils.millisecondsToWords(time) + "&7 ago");
        } else if (!offline.hasJoined()) {
            message.append("\n&7 - Has not joined the server");
        }

        message.append("\n&7 - UUID: &6" + stringUuid)
                .hoverEvent(HoverEvent.Action.SHOW_TEXT, "&7Click to copy &6" + stringUuid)
                .clickEvent(ClickEvent.Action.SUGGEST_COMMAND, stringUuid);

        if (nickname != null) {
            nickname = nickname.replace(ChatColor.COLOR_CHAR, '&');

            message.append("\n&7 - Nickname: &6" + nickname)
                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, "&7Click to copy &6" + nickname)
                    .clickEvent(ClickEvent.Action.SUGGEST_COMMAND, nickname);
        }

        if (address != null && PermissionType.ADMIN.hasPermission(sender))
            message.append("\n&7 - IP Address: &6" + address)
                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, "&7Click to copy &6" + address)
                    .clickEvent(ClickEvent.Action.SUGGEST_COMMAND, address);

        if (offline.isBanned()) {
            PunishData ban = offline.getBan();
            Long expire = ban.getExpire();
            String date = expire == null ? "never" : TimeUtils.TIME_FORMAT.format(new Date(expire));
            String reason = ban.getReason();

            message.append("\n&7 - Banned: &6" + reason)
                    .hoverEvent(HoverEvent.Action.SHOW_TEXT,
                            "&6Banned by &7" + ban.getBanner()
                                    + "\n&6Expires &7" + date
                                    + "\n&6For &7" + reason);
        }

        if (offline.isMuted()) {
            PunishData mute = offline.getMute();
            Long expire = mute.getExpire();
            String date = expire == null ? "never" : TimeUtils.TIME_FORMAT.format(new Date(expire));
            String reason = mute.getReason();

            message.append("\n&7 - Muted: &6" + reason)
                    .hoverEvent(HoverEvent.Action.SHOW_TEXT,
                            "&6Muted by &7" + mute.getBanner()
                                    + "\n&6Expires &7" + date
                                    + "\n&6For &7" + reason);
        }

        MessageUtils.message(sender, message.build());
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return CompletionUtils.offline(args[0]);
        return Collections.EMPTY_LIST;
    }

}
