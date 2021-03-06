package xyz.dashnetwork.core.bungee.command.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.utils.*;
import xyz.dashnetwork.core.utils.ColorUtils;
import xyz.dashnetwork.core.utils.MessageBuilder;

import java.util.Collections;

public class CommandNickname extends CoreCommand {

    public CommandNickname() {
        super(true, PermissionType.NONE, "nickname", "nick");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        int length = args.length;
        ProxiedPlayer target = null;

        if (length == 0) {
            MessageUtils.message(sender, "&6&l»&7 /nickname <name/off>");
            return;
        }

        String arg = args[0];
        boolean admin = PermissionType.ADMIN.hasPermission(sender);

        if (length > 1 && admin)
            target = SelectorUtils.getPlayer(sender, arg);
        else if (sender instanceof ProxiedPlayer)
            target = (ProxiedPlayer) sender;

        if (target == null) {
            Messages.noPlayerFound(sender);
            return;
        }

        User user = User.getUser(target);
        String input = sender.equals(target) ? arg : args[1];

        if (!admin)
            input = ColorUtils.filter(input, true, false, true, false, false, false);

        if (input.equalsIgnoreCase("off")) {
            user.setNickname(null);
            MessageUtils.message(target, "&6&l»&7 You no longer have a nickname");

            if (sender != target) {
                MessageBuilder builder = new MessageBuilder();
                builder.append("&6&l» ");
                builder.append("&6" + user.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + user.getName());
                builder.append("&7 no longer has a nickname");

                MessageUtils.message(sender, builder.build());
            }

            return;
        }

        if (!admin && input.length() > 16) {
            MessageUtils.message(sender, "&6&l»&c That nickname is too long");
            return;
        }

        String nickname = ColorUtils.translate(input);

        if (!admin && DataUtils.getNames().containsValue(ChatColor.stripColor(nickname))) {
            MessageUtils.message(sender, "&6&l»&c You are not allowed to use that name");
            return;
        }

        user.setNickname(nickname);

        MessageUtils.message(target, "&6&l»&7 Your nickname is now &6" + nickname);

        if (sender != target) {
            MessageBuilder builder = new MessageBuilder();
            builder.append("&6&l» ");
            builder.append("&6" + user.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + user.getName());
            builder.append("&7 nickname set to &6" + nickname);

            MessageUtils.message(sender, builder.build());
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
