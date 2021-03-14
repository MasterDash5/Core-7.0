package xyz.dashnetwork.core.bungee.command.commands;

import net.md_5.bungee.api.CommandSender;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.utils.CompletionUtils;
import xyz.dashnetwork.core.bungee.utils.MessageUtils;
import xyz.dashnetwork.core.bungee.utils.Messages;
import xyz.dashnetwork.core.bungee.utils.PermissionType;
import xyz.dashnetwork.core.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandFakeleave extends CoreCommand {

    public CommandFakeleave() {
        super(true, PermissionType.ADMIN, "fakeleave");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length <= 1) {
            MessageUtils.message(sender, "&6&l» &7/fakeleave <username> <displayname>");
            return;
        }

        List<String> list = new ArrayList<>(Arrays.asList(args));
        list.remove(0);

        String username = args[0];
        String displayname = StringUtils.unsplit(list, ' ');

        Messages.leaveServer(username, displayname);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return CompletionUtils.players(sender, args[0]);
        return Collections.EMPTY_LIST;
    }

}
