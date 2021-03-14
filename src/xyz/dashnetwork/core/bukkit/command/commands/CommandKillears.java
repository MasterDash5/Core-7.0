package xyz.dashnetwork.core.bukkit.command.commands;

import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dashnetwork.core.bukkit.command.CoreCommand;
import xyz.dashnetwork.core.bukkit.utils.*;
import xyz.dashnetwork.core.utils.MessageBuilder;
import xyz.dashnetwork.core.utils.StringUtils;

import java.util.Collection;
import java.util.Collections;

public class CommandKillears extends CoreCommand {

    public CommandKillears() {
        super(true, PermissionType.OWNER, "killears");
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (args.length <= 0) {
            MessageUtils.message(sender, "&6&l» &7/killears <player>");
            return;
        }

        Collection<Player> targets = SelectorUtils.getPlayers(sender, args[0]);

        if (targets.isEmpty()) {
            MessageUtils.noPlayerFound(sender);
            return;
        }

        for (Player target : targets) {
            MessageUtils.message(target, "&c&lRIP YOUR EARS");

            for (int i = 0; i < 3; i++)
                for (Sound sound : Sound.values())
                    target.playSound(target.getLocation(), sound, 10.0F, 1.0F);
        }

        String displaynames = StringUtils.fromList(NameUtils.toDisplayNames(targets), false ,false);
        String names = StringUtils.fromList(NameUtils.toNames(targets), false, false);

        MessageBuilder message = new MessageBuilder();
        message.append("&6&l» &7Killearsed ");
        message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);

        MessageUtils.message(sender, message.build());
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1)
            return CompletionUtils.players(sender, args[0]);
        return Collections.EMPTY_LIST;
    }

}
