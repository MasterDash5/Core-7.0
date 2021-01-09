package dashnetwork.core.bungee.utils;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class VanishUtils {

    public static boolean canSee(CommandSender sender, CommandSender target) {
        if (sender.equals(target))
            return true;

        if (sender instanceof ProxiedPlayer && target instanceof ProxiedPlayer) {
            User user1 = User.getUser((ProxiedPlayer) sender);
            User user2 = User.getUser((ProxiedPlayer) target);

            if (!user1.isStaff() && user2.isVanished())
                return false;
        }

        return true;
    }

}
