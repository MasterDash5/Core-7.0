package dashnetwork.core.bukkit.command.commands;

import dashnetwork.core.bukkit.command.CoreCommand;
import dashnetwork.core.bukkit.utils.CompletionUtils;
import dashnetwork.core.bukkit.utils.MessageUtils;
import dashnetwork.core.bukkit.utils.PermissionType;
import dashnetwork.core.bukkit.utils.User;
import dashnetwork.core.utils.GrammarUtils;
import dashnetwork.core.utils.MessageBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class CommandGamemode extends CoreCommand {

    public CommandGamemode() {
        super(false, PermissionType.ADMIN, "gamemode");
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        int length = args.length;
        GameMode selected = null;
        Player target = null;

        switch (label) {
            case "gms":
                selected = GameMode.SURVIVAL;
                break;
            case "gmc":
                selected = GameMode.CREATIVE;
                break;
            case "gma":
                selected = GameMode.ADVENTURE;
                break;
            case "gmsp":
                selected = GameMode.SPECTATOR;
                break;
            default:
                if (length > 0) {
                    try {
                        selected = GameMode.valueOf(args[0].toUpperCase());
                    } catch (IllegalArgumentException e1) {}
                }
                break;
        }

        if (args.length > 1)
            target = Bukkit.getPlayer(args[1]);
        else if (sender instanceof Player)
            target = (Player) sender;


        if (selected == null || target == null) {
            MessageUtils.message(sender, "&6&l» &7/gamemode <gamemode> <player>");
            return;
        }

        String display = GrammarUtils.capitalization(selected.name());

        target.setGameMode(selected);

        MessageUtils.message(target, "&6&l» &7You are now in &6" + display);

        if (target != sender) {
            User user = User.getUser(target);

            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» ");
            message.append("&6" + user.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + target.getName());
            message.append("&7 is now in &6" + display);
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length > 0)
            return CompletionUtils.players(sender, args[0]);
        return Collections.EMPTY_LIST;
    }

}
