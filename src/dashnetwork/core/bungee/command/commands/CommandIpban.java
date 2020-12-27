package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.*;
import dashnetwork.core.utils.StringUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandIpban extends CoreCommand {

    public CommandIpban() {
        super(true, PermissionType.OWNER, "ipban", "banip");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        int length = args.length;

        if (length <= 0) {
            MessageUtils.message(sender, "&6&l» &7/ipban <player/address> <reason>");
            return;
        }

        String address = args[0];

        if (!address.contains(".")) {
            ProxiedPlayer target = SelectorUtils.getPlayer(sender, args[0]);

            if (target == null) {
                MessageUtils.message(sender, "&6&l» &cInvalid IP address");
                return;
            }

            address = ((InetSocketAddress) target.getSocketAddress()).getAddress().getHostAddress();
        }

        String reason = "no reason provided";

        if (length > 1) {
            List<String> list = new ArrayList<>(Arrays.asList(args));
            list.remove(0);

            reason = StringUtils.unsplit(list, ' ');
        }

        PunishUtils.ipban(address, null, sender, reason);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return CompletionUtils.players(args[0]);
        return Collections.EMPTY_LIST;
    }

}
