package xyz.dashnetwork.core.bukkit.command.commands;

import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Statistic;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dashnetwork.core.bukkit.command.CoreCommand;
import xyz.dashnetwork.core.bukkit.utils.*;
import xyz.dashnetwork.core.utils.MessageBuilder;
import xyz.dashnetwork.core.utils.ProtocolVersion;
import xyz.dashnetwork.core.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandSleep extends CoreCommand {

    public CommandSleep() {
        super(false, PermissionType.STAFF, "sleep");
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (ProtocolVersion.v1_13.isNewerThan(PlatformUtils.getServerVersion())) {
            MessageUtils.message(sender, "&6&l» &7This command is &61.13+ &7only");
            return;
        }

        List<Player> targets = new ArrayList<>();

        if (args.length > 0)
            targets.addAll(SelectorUtils.getPlayers(sender, args[0]));
        else if (sender instanceof Player)
            targets.add((Player) sender);

        if (targets.isEmpty()) {
            MessageUtils.noPlayerFound(sender);
            return;
        }

        List<Player> reset = new ArrayList<>();

        for (Player target : targets) {
            try {
                Class statistic = Class.forName("org.bukkit.Statistic");
                Statistic rest = (Statistic) Enum.valueOf(statistic, "TIME_SINCE_REST");

                target.setStatistic(rest, 0);
            } catch (Exception exception) {}

            MessageUtils.message(target, "&6&l» &7Your sleep timer has been reset");

            if (target != sender)
                reset.add(target);
        }

        if (!reset.isEmpty()) {
            String displaynames = StringUtils.fromList(NameUtils.toDisplayNames(reset), false, false);
            String names = StringUtils.fromList(NameUtils.toNames(reset), false, false);

            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» ");
            message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
            message.append("&7 sleep timer reset");

            sender.sendMessage(message.build());
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
