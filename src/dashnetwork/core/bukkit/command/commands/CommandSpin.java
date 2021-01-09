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

public class CommandSpin extends CoreCommand {

    public CommandSpin() {
        super(true, PermissionType.OWNER, "spin");
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        List<Player> targets = new ArrayList<>();

        if (args.length > 0)
            targets.addAll(SelectorUtils.getPlayers(sender, args[0]));
        else if (sender instanceof Player)
            targets.add((Player) sender);

        if (targets.isEmpty()) {
            MessageUtils.noPlayerFound(sender);
            return;
        }

        List<Player> added = new ArrayList<>();
        List<Player> removed = new ArrayList<>();

        for (Player target : targets) {
            User user = User.getUser(target);
            boolean spinning = !user.isSpinning();

            user.setSpinning(spinning);

            if (spinning) {
                MessageUtils.message(target, "&6&l» &7You spin me right round baby right round");

                if (!target.equals(sender))
                    added.add(target);
            } else {
                MessageUtils.message(target, "&6&l» &7You are no longer spinning");

                if (!target.equals(sender))
                    removed.add(target);
            }
        }

        if (!added.isEmpty()) {
            String displaynames = StringUtils.fromList(NameUtils.toDisplayNames(added), false, false);
            String names = StringUtils.fromList(NameUtils.toNames(added), false, false);

            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» ");
            message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
            message.append("&7 " + (added.size() > 1 ? "are" : "is") + " now spinning");

            sender.sendMessage(message.build());
        }

        if (!removed.isEmpty()) {
            String displaynames = StringUtils.fromList(NameUtils.toDisplayNames(removed), false, false);
            String names = StringUtils.fromList(NameUtils.toNames(removed), false, false);

            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» ");
            message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
            message.append("&7 " + (removed.size() > 1 ? "are" : "is") + " no longer spinning");

            sender.sendMessage(message.build());
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1)
            return CompletionUtils.players(args[0]);
        return Collections.EMPTY_LIST;
    }

}
