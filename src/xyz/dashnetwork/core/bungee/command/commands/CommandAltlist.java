package xyz.dashnetwork.core.bungee.command.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.utils.*;
import xyz.dashnetwork.core.utils.ListUtils;
import xyz.dashnetwork.core.utils.StringUtils;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandAltlist extends CoreCommand {

    public CommandAltlist() {
        super(true, PermissionType.STAFF, "altlist", "alts");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length <= 0) {
            MessageUtils.message(sender, "&6&l» &7/altlist <player>");
            return;
        }

        ProxiedPlayer target = SelectorUtils.getPlayer(sender, args[0]);

        if (target == null) {
            Messages.noPlayerFound(sender);
            return;
        }

        String uuid = target.getUniqueId().toString();
        String address = ((InetSocketAddress) target.getSocketAddress()).getAddress().getHostAddress();
        List<String> accounts = DataUtils.getIps().getOrDefault(address, Collections.EMPTY_LIST);

        if (ListUtils.containsOtherThan(accounts, uuid)) {
            List<String> names = new ArrayList<>();

            for (String account : accounts)
                if (!account.equals(uuid))
                    names.add(DataUtils.getNames().getOrDefault(account, "Unknown"));

            MessageUtils.message(sender, "&6&l» &7Alts: &6" + StringUtils.fromList(names, false, true));
        } else
            MessageUtils.message(sender, "&6&l» &7No alts found.");
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return CompletionUtils.players(sender, args[0]);
        return Collections.EMPTY_LIST;
    }

}
