package dashnetwork.core.bukkit.command.commands;

import dashnetwork.core.bukkit.command.CoreCommand;
import dashnetwork.core.bukkit.utils.CompletionUtils;
import dashnetwork.core.bukkit.utils.MessageUtils;
import dashnetwork.core.bukkit.utils.PermissionType;
import dashnetwork.core.bukkit.utils.SelectorUtils;
import dashnetwork.core.utils.MessageBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class CommandPing extends CoreCommand {

    public CommandPing() {
        super(true, PermissionType.NONE, "ping");
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        Player target = null;

        if (args.length > 0)
            target = SelectorUtils.getPlayer(sender, args[0]);
        else if (sender instanceof Player)
            target = (Player) sender;

        if (target == null) {
            MessageUtils.noPlayerFound(sender);
            return;
        }

        MessageBuilder message = new MessageBuilder();
        message.append("&6&l» ");
        message.append("&6" + target.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + target.getName());
        message.append(" &7ping: &6" + target.spigot().getPing() + "ms");

        MessageUtils.message(sender, message.build());
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1)
            return CompletionUtils.players(args[0]);
        return Collections.EMPTY_LIST;
    }

}
