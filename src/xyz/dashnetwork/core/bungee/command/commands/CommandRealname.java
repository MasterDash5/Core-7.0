package xyz.dashnetwork.core.bungee.command.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.HoverEvent;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.utils.MessageUtils;
import xyz.dashnetwork.core.bungee.utils.Messages;
import xyz.dashnetwork.core.bungee.utils.PermissionType;
import xyz.dashnetwork.core.bungee.utils.User;
import xyz.dashnetwork.core.utils.ColorUtils;
import xyz.dashnetwork.core.utils.MessageBuilder;
import xyz.dashnetwork.core.utils.StringUtils;

import java.util.Collections;

public class CommandRealname extends CoreCommand {

    public CommandRealname() {
        super(true, PermissionType.NONE, "realname");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            MessageUtils.message(sender, "&6&l» &7/realname <nickname>");
            return;
        }

        boolean found = false;

        for (User user : User.getUsers(true)) {
            String nickname = user.getNickname();
            String name = user.getName();

            if (nickname == null)
                nickname = name;

            nickname = ColorUtils.strip(nickname);

            if (StringUtils.containsIgnoreCase(nickname, args[0])) {
                found = true;

                MessageBuilder message = new MessageBuilder();
                message.append("&6&l» ");
                message.append("&6" + user.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + name);
                message.append(" &7realname is &6" + name);

                MessageUtils.message(sender, message.build());
            }
        }

        if (!found)
            Messages.noPlayerFound(sender);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
