package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

public class CommandPingspy extends CoreCommand {

    public CommandPingspy() {
        super(true, PermissionType.ADMIN, "pingspy");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            List<ProxiedPlayer> targets = new ArrayList<>();

            if (args.length > 0) {
                targets.addAll(SelectorUtils.getPlayers(args[0]));

                if (targets.isEmpty()) {
                    MessageUtils.message(sender, "&6&l» &cNo players were found.");
                    return;
                }
            } else
                targets.add(player);

            List<ProxiedPlayer> added = new ArrayList<>();
            List<ProxiedPlayer> removed = new ArrayList<>();

            for (ProxiedPlayer target : targets) {
                User user = User.getUser(target);
                boolean pingspy = !user.inPingSpy();

                user.setPingSpy(pingspy);

                if (pingspy) {
                    MessageUtils.message(target, ColorUtils.translate("&6&l» &7You are now in PingSpy"));

                    if (!target.equals(player))
                        added.add(target);
                } else {
                    MessageUtils.message(target, ColorUtils.translate("&6&l» &7You are no longer in PingSpy"));

                    if (!target.equals(player))
                        removed.add(target);
                }
            }

            if (!added.isEmpty()) {
                String displaynames = ListUtils.fromList(NameUtils.toDisplayNames(added), false, false);
                String names = ListUtils.fromList(NameUtils.toNames(added), false, false);

                MessageBuilder message = new MessageBuilder();
                message.append("&6&l» ");
                message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
                message.append("&7 " + (added.size() > 1 ? "are" : "is") + " now in PingSpy");

                player.sendMessage(message.build());
            }

            if (!removed.isEmpty()) {
                String displaynames = ListUtils.fromList(NameUtils.toDisplayNames(removed), false, false);
                String names = ListUtils.fromList(NameUtils.toNames(removed), false, false);

                MessageBuilder message = new MessageBuilder();
                message.append("&6&l» ");
                message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
                message.append("&7 " + (removed.size() > 1 ? "are" : "is") + " no longer in PingSpy");

                player.sendMessage(message.build());
            }
        } else
            MessageUtils.playersOnly();
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return NameUtils.toNames(bungee.getPlayers());
    }

}