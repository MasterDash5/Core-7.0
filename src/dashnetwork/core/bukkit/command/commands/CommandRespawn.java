package dashnetwork.core.bukkit.command.commands;

import dashnetwork.core.bukkit.command.CoreCommand;
import dashnetwork.core.bukkit.utils.*;
import dashnetwork.core.utils.ListUtils;
import dashnetwork.core.utils.MessageBuilder;
import dashnetwork.core.utils.StringUtils;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandRespawn extends CoreCommand {

    public CommandRespawn() {
        super(false, PermissionType.ADMIN, "respawn");
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        List<Player> targets = new ArrayList<>();

        if (args.length > 0)
            targets.addAll(SelectorUtils.getPlayers(sender, args[0]));

        if (targets.isEmpty()) {
            MessageUtils.noPlayerFound(sender);
            return;
        }

        for (Player target : targets)
            target.spigot().respawn();

        String displaynames = StringUtils.fromList(NameUtils.toDisplayNames(targets), false, false);
        String names = StringUtils.fromList(NameUtils.toNames(targets), false, false);

        MessageBuilder message = new MessageBuilder();
        message.append("&6&l» ");
        message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
        message.append("&7 forced to respawn");

        sender.sendMessage(message.build());
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1)
            return CompletionUtils.players(args[0]);
        return Collections.EMPTY_LIST;
    }

}
