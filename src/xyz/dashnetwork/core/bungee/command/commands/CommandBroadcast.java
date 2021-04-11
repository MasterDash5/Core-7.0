package xyz.dashnetwork.core.bungee.command.commands;

import net.md_5.bungee.api.CommandSender;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.utils.MessageUtils;
import xyz.dashnetwork.core.bungee.utils.PermissionType;
import xyz.dashnetwork.core.utils.Channel;
import xyz.dashnetwork.core.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandBroadcast extends CoreCommand {

    public CommandBroadcast() {
        super(true, PermissionType.OWNER, "broadcast", "bc");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length < 2) {
            MessageUtils.message(sender, "&6&l» &7/broadcast <channel> <message>");
            return;
        }

        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(args));

        Channel channel;

        try {
            channel = Channel.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException exception) {
            MessageUtils.message(sender, "&6&l» &7Valid channels: &6Global&7, &6Staff&7, &6Admin&7, &6Owner");
            return;
        }

        if (channel.equals(Channel.LOCAL)) {
            MessageUtils.message(sender, "&6&l» &6Local &7is not supported here");
            return;
        }

        list.remove(0);

        PermissionType permission = PermissionType.fromChannel(channel);
        String message = StringUtils.unsplit(list, ' ');

        MessageUtils.broadcast(permission, message);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
