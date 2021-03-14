package xyz.dashnetwork.core.bungee.command.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.utils.*;
import xyz.dashnetwork.core.utils.GrammarUtils;
import xyz.dashnetwork.core.utils.MessageBuilder;
import xyz.dashnetwork.core.utils.ProtocolVersion;

import java.net.InetSocketAddress;
import java.util.Collections;

public class CommandPlayerinfo extends CoreCommand {

    public CommandPlayerinfo() {
        super(true, PermissionType.ADMIN, "playerinfo");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length > 0) {
            ProxiedPlayer target = SelectorUtils.getPlayer(sender, args[0]);

            if (target == null) {
                Messages.noPlayerFound(sender);
                return;
            }

            User user = User.getUser(target);
            String displayname = user.getDisplayName().replace(ChatColor.COLOR_CHAR, '&');
            String uuid = target.getUniqueId().toString();
            String address = ((InetSocketAddress) target.getSocketAddress()).getAddress().getHostAddress();
            String version = ProtocolVersion.fromId(target.getPendingConnection().getVersion()).getName();
            String server = target.getServer().getInfo().getName();

            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» &6" + GrammarUtils.possessive(target.getName()) + "&7 player info");
            message.append("\n&6&l» &7DisplayName - &6" + displayname)
                    .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, displayname)
                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, "&7Click to copy &6" + displayname);
            message.append("\n&6&l» &7UUID - &6" + uuid)
                    .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, uuid)
                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, "&7Click to copy &6" + uuid);

            if (PermissionType.ADMIN.hasPermission(sender)) {
                message.append("\n&6&l» &7IP - &6" + address)
                        .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, address)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, "&7Click to copy &6" + address);
            }

            message.append("\n&6&l» &7Version - &6" + version);
            message.append("\n&6&l» &7Server - &6" + server);

            sender.sendMessage(message.build());
        } else
            MessageUtils.message(sender, "&6&l» &7/playerinfo <player>");
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return CompletionUtils.players(sender, args[0]);
        return Collections.EMPTY_LIST;
    }

}
