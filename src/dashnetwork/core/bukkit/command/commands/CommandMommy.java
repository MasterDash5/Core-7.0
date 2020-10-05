package dashnetwork.core.bukkit.command.commands;

import dashnetwork.core.bukkit.command.CoreCommand;
import dashnetwork.core.bukkit.utils.MessageUtils;
import dashnetwork.core.bukkit.utils.PermissionType;
import dashnetwork.core.utils.GrammarUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class CommandMommy extends CoreCommand {

    private String[] mommies = { "Dash", "Matt", "Furp", "Skull", "Golden", "Hannah", "Ryan", "Gibby" };

    public CommandMommy() {
        super(true, PermissionType.NONE, "mommy");
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        String member = GrammarUtils.capitalization(label);
        String random = mommies[ThreadLocalRandom.current().nextInt(mommies.length)];

        MessageUtils.message(sender, "&6&l» &7Scanning face for " + member + "...");

        new BukkitRunnable() {
            @Override
            public void run() {
                MessageUtils.message(sender, "&6&l» &7Face identified. Your " + member + " is &6" + random);
            }
        }.runTaskLaterAsynchronously(plugin, 20);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
