package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.*;
import dashnetwork.core.utils.ColorUtils;
import dashnetwork.core.utils.StringUtils;
import dashnetwork.core.utils.TimeUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandTempipban extends CoreCommand {

    public CommandTempipban() {
        super(true, PermissionType.OWNER, "tempipban", "tempbanip", "ipbantemp", "baniptemp");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        int length = args.length;

        if (length <= 1) {
            MessageUtils.message(sender, ColorUtils.translate("&6&l» &7/tempipban <player/address> <time> <reason>"));
            return;
        }

        String address = args[0];

        if (!address.contains(".")) {
            ProxiedPlayer target = SelectorUtils.getPlayer(sender, args[0]);

            if (target == null) {
                MessageUtils.message(sender, ColorUtils.translate("&6&l» &cInvalid IP address"));
                return;
            }

            address = ((InetSocketAddress) target.getSocketAddress()).getAddress().getHostAddress();
        }

        Long input = TimeUtils.fromTimeArgument(args[1]);

        if (input == null) {
            MessageUtils.message(sender, ColorUtils.translate("&6&l» &cInvalid time input"));
            return;
        }

        long duration = System.currentTimeMillis() + input;
        String reason = "no reason provided";

        if (length > 1) {
            List<String> list = new ArrayList<>(Arrays.asList(args));
            list.remove(0);
            list.remove(0);

            reason = StringUtils.unsplit(list, ' ');
        }

        PunishUtils.ipban(address, duration, sender, reason);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return NameUtils.toNames(bungee.getPlayers());
        return Collections.EMPTY_LIST;
    }

}
