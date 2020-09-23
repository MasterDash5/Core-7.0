package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.MessageUtils;
import dashnetwork.core.bungee.utils.PermissionType;
import dashnetwork.core.bungee.utils.User;
import dashnetwork.core.utils.ColorUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.ChatColor;

import java.util.Collections;

public class CommandNickname extends CoreCommand {

    public CommandNickname() {
        super(true, PermissionType.NONE, "nickname", "nick");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        // TODO: Edit other player's nickname

        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            User user = User.getUser(player);

            if (args.length <= 0) {
                MessageUtils.message(sender, ColorUtils.translate("&6&l» &7/nickname <name/off>"));
                return;
            }

            String nickname = ColorUtils.translate(args[0]);

            if (ChatColor.stripColor(nickname).length() > 16) {
                MessageUtils.message(sender, "&6&l» &cThat nickname is too long");
                return;
            }

            user.setNickname(nickname);

            MessageUtils.message(sender, "6&l» &7Your nickname is now &6" + nickname);
        } else
            MessageUtils.playersOnly();
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
