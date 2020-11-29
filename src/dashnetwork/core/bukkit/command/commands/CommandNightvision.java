package dashnetwork.core.bukkit.command.commands;

import dashnetwork.core.bukkit.command.CoreCommand;
import dashnetwork.core.bukkit.utils.MessageUtils;
import dashnetwork.core.bukkit.utils.PermissionType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;

public class CommandNightvision extends CoreCommand {

    public CommandNightvision() {
        super(false, PermissionType.ADMIN, "nightvision");
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION))
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            else
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 0, true));
        } else
            MessageUtils.playersOnly();
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return Collections.EMPTY_LIST;
    }

}