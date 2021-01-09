package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.*;
import dashnetwork.core.utils.MessageBuilder;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandClearchat extends CoreCommand {

    public CommandClearchat() {
        super(true, PermissionType.ADMIN, "clearchat", "cc");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        List<ProxiedPlayer> targets = new ArrayList<>();

        if (args.length > 0)
            targets.addAll(SelectorUtils.getPlayers(sender, args[0]));
        else
            targets.addAll(bungee.getPlayers());

        if (targets.isEmpty()) {
            Messages.noPlayerFound(sender);
            return;
        }

        MessageBuilder message = new MessageBuilder();

        for (int i = 0; i < 150; i++)
            message.append("\n");

        message.append("&6&l» &7Chat was cleared by ");
        message.append("&6" + NameUtils.getDisplayName(sender)).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + NameUtils.getName(sender));

        for (ProxiedPlayer target : targets)
            target.sendMessage(message.build());
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return CompletionUtils.players(sender, args[0]);
        return Collections.EMPTY_LIST;
    }

}
