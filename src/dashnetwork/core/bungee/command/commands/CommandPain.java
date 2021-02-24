package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.Core;
import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.pain.Pain;
import dashnetwork.core.bungee.utils.CompletionUtils;
import dashnetwork.core.bungee.utils.MessageUtils;
import dashnetwork.core.bungee.utils.Messages;
import dashnetwork.core.bungee.utils.PermissionType;
import net.md_5.bungee.api.CommandSender;

import java.util.Collections;

public class CommandPain extends CoreCommand {

    public enum Argument { START, STOP, STACKTRACE }

    public CommandPain() {
        super(true, PermissionType.OWNER, "pain");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            MessageUtils.message(sender, "&6&l» &7/pain <start,stop,stacktrace>");
            return;
        }

        Argument argument;

        try {
            argument = Argument.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException exception) {
            MessageUtils.message(sender, "&6&l» &7/pain <start,stop,stacktrace>");
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
            case STACKTRACE:
                Messages.printStackTrace(sender, pain.getStacktrace());
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
