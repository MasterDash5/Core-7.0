package xyz.dashnetwork.core.bukkit.command.commands;

import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dashnetwork.core.bukkit.command.CoreCommand;
import xyz.dashnetwork.core.bukkit.utils.*;
import xyz.dashnetwork.core.utils.GrammarUtils;
import xyz.dashnetwork.core.utils.MessageBuilder;

import java.util.Collections;

public class CommandGamemode extends CoreCommand {

    public CommandGamemode() {
        super(false, PermissionType.ADMIN, "gamemode");
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        int length = args.length;
        boolean multiarg = false;
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
            case "gm":
            case "gamemode":
                if (length > 0) {
                    try {
                        selected = GameMode.valueOf(args[0].toUpperCase());
                    } catch (IllegalArgumentException exception) {}
                }
                multiarg = true;
                break;
        }

        if (selected == null) {
            MessageUtils.message(sender, "&6&l» &7/gamemode <gamemode> <player>");
            return;
        }

        if ((length > 0 && !multiarg) || length > 1)
            target = SelectorUtils.getPlayer(sender, args[length - 1]);
        else if (sender instanceof Player)
            target = (Player) sender;

        if (target == null) {
            MessageUtils.noPlayerFound(sender);
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
