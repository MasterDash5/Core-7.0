package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.*;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandCommandspy extends CoreCommand {

    public CommandCommandspy() {
        super(true, PermissionType.STAFF, "commandspy", "cmdspy", "cs");
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
            boolean commandspy = !user.inCommandSpy();

            user.setCommandSpy(commandspy);

            if (commandspy) {
                MessageUtils.message(target, "&6&l» &7You are now in CommandSpy");

                if (!target.equals(sender))
                    added.add(target);
            } else {
                MessageUtils.message(target, "&6&l» &7You are no longer in CommandSpy");

                if (!target.equals(sender))
                    removed.add(target);
            }
        }

        if (!added.isEmpty())
            Messages.targetNowIn(sender, added, "CommandSpy");

        if (!removed.isEmpty())
            Messages.targetNoLongerIn(sender, removed, "CommandSpy");
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1 && PermissionType.ADMIN.hasPermission(sender))
            return CompletionUtils.players(sender, args[0]);
        return Collections.EMPTY_LIST;
    }

}
