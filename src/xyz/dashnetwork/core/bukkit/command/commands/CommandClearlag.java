package xyz.dashnetwork.core.bukkit.command.commands;

import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import xyz.dashnetwork.core.bukkit.command.CoreCommand;
import xyz.dashnetwork.core.bukkit.utils.MessageUtils;
import xyz.dashnetwork.core.bukkit.utils.NameUtils;
import xyz.dashnetwork.core.bukkit.utils.PermissionType;
import xyz.dashnetwork.core.utils.LazyUtils;
import xyz.dashnetwork.core.utils.MessageBuilder;
import xyz.dashnetwork.core.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandClearlag extends CoreCommand {

    public CommandClearlag() {
        super(true, PermissionType.ADMIN, "clearlag");
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        List<String> cleared = new ArrayList<>();

        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getCustomName() == null) {
                    EntityType type = entity.getType();
                    String name = entity.getName();

                    if (LazyUtils.anyEquals(type, EntityType.DROPPED_ITEM, EntityType.EXPERIENCE_ORB)) {
                        entity.remove();
                        cleared.add(name);
                    }

                    if (type.equals(EntityType.ARROW) && entity.isOnGround()) {
                        entity.remove();
                        cleared.add(name);
                    }
                }
            }
        }

        MessageBuilder message = new MessageBuilder();
        message.append("&6&l» ");
        message.append("&6" + NameUtils.getDisplayName(sender)).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + NameUtils.getName(sender));
        message.append(" ");
        message.append("&7cleared &6" + cleared.size() + " entities").hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + StringUtils.fromList(cleared, false, true));

        MessageUtils.broadcast(PermissionType.NONE, message.build());
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
