package xyz.dashnetwork.core.bungee.command.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.utils.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandSignspy extends CoreCommand {

    public CommandSignspy() {
        super(true, PermissionType.STAFF, "signspy");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        List<ProxiedPlayer> targets = new ArrayList<>();

        if (args.length > 0 && PermissionType.ADMIN.hasPermission(sender))
            targets.addAll(SelectorUtils.getPlayers(sender, args[0]));
        else if (sender instanceof ProxiedPlayer)
            targets.add((ProxiedPlayer) sender);

        if (targets.isEmpty()) {
            Messages.noPlayerFound(sender);
            return;
        }

        List<ProxiedPlayer> added = new ArrayList<>();
        List<ProxiedPlayer> removed = new ArrayList<>();

        for (ProxiedPlayer target : targets) {
            User user = User.getUser(target);
            boolean signspy = !user.inSignSpy();

            user.setSignSpy(signspy);

            if (signspy) {
                MessageUtils.message(target, "&6&l» &7You are now in SignSpy");

                if (!target.equals(sender))
                    added.add(target);
            } else {
                MessageUtils.message(target, "&6&l» &7You are no longer in SignSpy");

                if (!target.equals(sender))
                    removed.add(target);
            }
        }

        if (!added.isEmpty())
            Messages.targetNowIn(sender, added, "SignSpy");

        if (!removed.isEmpty())
            Messages.targetNoLongerIn(sender, removed, "SignSpy");
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1 && PermissionType.ADMIN.hasPermission(sender))
            return CompletionUtils.players(sender, args[0]);
        return Collections.EMPTY_LIST;
    }

}
