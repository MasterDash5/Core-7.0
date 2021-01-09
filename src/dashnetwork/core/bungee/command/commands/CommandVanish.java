package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.*;
import dashnetwork.core.utils.ListUtils;
import dashnetwork.core.utils.MessageBuilder;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandVanish extends CoreCommand {

    public CommandVanish() {
        super(true, PermissionType.STAFF, "vanish", "v");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        List<ProxiedPlayer> targets = new ArrayList<>();

        if (args.length > 0 && PermissionType.ADMIN.hasPermission(sender))
            targets.addAll(SelectorUtils.getPlayers(sender, args[0]));
        else if (sender instanceof ProxiedPlayer)
            targets.add((ProxiedPlayer) sender);
        else {
            MessageUtils.message(sender, "&6&l» &7/vanish <player>");
            return;
        }

        List<ProxiedPlayer> added = new ArrayList<>();
        List<ProxiedPlayer> removed = new ArrayList<>();

        for (ProxiedPlayer target : targets) {
            User user = User.getUser(target);
            boolean vanished = !user.isVanished();

            user.setVanished(vanished);

            if (vanished) {
                MessageUtils.message(target, "&6&l» &7You are now vanished");

                if (!target.equals(sender))
                    added.add(target);
            } else {
                MessageUtils.message(target, "&6&l» &7You are no longer vanished");

                if (!target.equals(sender))
                    removed.add(target);
            }
        }

        if (!added.isEmpty())
            Messages.targetNowIn(sender, added, "Vanish");

        if (!removed.isEmpty())
            Messages.targetNoLongerIn(sender, removed, "Vanish");
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1 && PermissionType.ADMIN.hasPermission(sender))
            return CompletionUtils.players(sender, args[0]);
        return Collections.EMPTY_LIST;
    }

}
