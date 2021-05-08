package xyz.dashnetwork.core.bungee.command.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.utils.DataUtils;
import xyz.dashnetwork.core.bungee.utils.MessageUtils;
import xyz.dashnetwork.core.bungee.utils.NameUtils;
import xyz.dashnetwork.core.bungee.utils.PermissionType;
import xyz.dashnetwork.core.utils.MessageBuilder;
import xyz.dashnetwork.core.utils.PageUtils;
import xyz.dashnetwork.core.utils.PunishData;
import xyz.dashnetwork.core.utils.TimeUtils;

import java.util.*;

public class CommandBanlist extends CoreCommand {

    public CommandBanlist() {
        super(true, PermissionType.STAFF, "banlist", "bans");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        int page = 1;

        if (args.length > 0) {
            try {
                page = Integer.valueOf(page);
            } catch (IllegalArgumentException exception) {
                MessageUtils.message(sender, "&6&l» &7Invalid page number.");
                return;
            }
        }

        List<BaseComponent[]> lines = new ArrayList<>();

        for (Map.Entry<String, PunishData> ban : DataUtils.getBans().entrySet()) {
            MessageBuilder builder = new MessageBuilder();
            String uuid = ban.getKey();
            String name = NameUtils.getUsername(UUID.fromString(uuid));

            PunishData data = ban.getValue();
            String banner = data.getBanner();
            String reason = data.getReason();
            Long expire = data.getExpire();

            String date = expire == null ? "never" : TimeUtils.TIME_FORMAT.format(new Date(expire));

            builder.append("&7 - &6" + name + " &7for &6" + reason)
                    .hoverEvent(HoverEvent.Action.SHOW_TEXT,
                            "&6" + uuid
                                    + "\n&6Banned by &7" + banner
                                    + "\n&6Expires &7" + date
                                    + "\n&6For &7" + reason);

            lines.add(builder.build());
        }

        MessageUtils.message(sender, PageUtils.createPagedMessage(page, lines));
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
