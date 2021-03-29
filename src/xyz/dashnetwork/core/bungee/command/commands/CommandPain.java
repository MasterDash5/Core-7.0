package xyz.dashnetwork.core.bungee.command.commands;

import net.md_5.bungee.api.CommandSender;
import xyz.dashnetwork.core.bungee.Core;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.pain.Pain;
import xyz.dashnetwork.core.bungee.utils.CompletionUtils;
import xyz.dashnetwork.core.bungee.utils.MessageUtils;
import xyz.dashnetwork.core.bungee.utils.Messages;
import xyz.dashnetwork.core.bungee.utils.PermissionType;

import java.util.Collections;

public class CommandPain extends CoreCommand {

    public enum Argument { START, STOP }

    public CommandPain() {
        super(true, PermissionType.OWNER, "pain");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            MessageUtils.message(sender, "&6&l» &7/pain <start,stop>");
            return;
        }

        Argument argument;

        try {
            argument = Argument.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException exception) {
            MessageUtils.message(sender, "&6&l» &7/pain <start,stop>");
            return;
        }

        Pain pain = Core.getPain();

        switch (argument) {
            case START:
                pain.start();
                MessageUtils.message(sender, "&6&l» &7Started &6Pain &7server");
                break;
            case STOP:
                pain.stop();
                MessageUtils.message(sender, "&6&l» &7Halted &6Pain &7server");
                break;
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 0)
            CompletionUtils.fromEnum(sender, args[0], Argument.values());
        return Collections.EMPTY_LIST;
    }

}
