package dashnetwork.core.bukkit.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishUtils {

    public static boolean canSee(CommandSender sender, CommandSender target) {
        if (sender.equals(target))
            return true;

        if (sender instanceof Player && target instanceof Player) {
            User user1 = User.getUser((Player) sender);
            User user2 = User.getUser((Player) target);

            if (!user1.isStaff() && user2.isVanished())
                return false;
        }

        return true;
    }

}
