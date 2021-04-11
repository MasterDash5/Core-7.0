package xyz.dashnetwork.core.bungee.command.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.utils.*;
import xyz.dashnetwork.core.utils.Channel;
import xyz.dashnetwork.core.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandChatsudo extends CoreCommand {

    public CommandChatsudo() {
        super(true, PermissionType.OWNER, "chatsudo", "csudo");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length > 1) {
            List<ProxiedPlayer> targets = new ArrayList<>(SelectorUtils.getPlayers(sender, args[0]));

            if (targets.isEmpty()) {
                Messages.noPlayerFound(sender);
                return;
            }

            Channel channel;

            try {
                channel = Channel.valueOf(args[1].toUpperCase());
            } catch (IllegalArgumentException exception) {
                MessageUtils.message(sender, "&6&l» &7Valid channels: &6Global&7, &6Local&7, &6Staff&7, &6Admin&7, &Owner");
                return;
            }

            List<String> list = new ArrayList<>(Arrays.asList(args));
            list.remove(0);
            list.remove(0);

            String message = StringUtils.unsplit(list, ' ');

            for (ProxiedPlayer target : targets)
                User.getUser(target).chat(channel, message);
        } else
            MessageUtils.message(sender, "&6&l» &7/chatsudo <player> <channel> <message>");
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return CompletionUtils.players(sender, args[0]);
        return Collections.EMPTY_LIST;
    }

}
