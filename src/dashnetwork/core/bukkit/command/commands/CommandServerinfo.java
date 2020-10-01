package dashnetwork.core.bukkit.command.commands;

import com.sun.management.OperatingSystemMXBean;
import dashnetwork.core.bukkit.command.CoreCommand;
import dashnetwork.core.bukkit.utils.*;
import dashnetwork.core.utils.ListUtils;
import dashnetwork.core.utils.MessageBuilder;
import dashnetwork.core.utils.StringUtils;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandServerinfo extends CoreCommand {

    private static OperatingSystemMXBean bean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
    private static DecimalFormat formatter = new DecimalFormat("#######.##");

    public CommandServerinfo() {
        super(true, PermissionType.ADMIN, "serverinfo");
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        int length = args.length;

        if (length > 0) {
            List<Player> targets = new ArrayList<>();

            if (length > 1 && PermissionType.OWNER.hasPermission(sender))
                targets.addAll(SelectorUtils.getPlayers(sender, args[1]));
            else if (sender instanceof Player)
                targets.add((Player) sender);

            if (targets.isEmpty()) {
                MessageUtils.noPlayerFound(sender);
                return;
            }

            List<Player> added = new ArrayList<>();
            List<Player> removed = new ArrayList<>();

            for (Player target : targets) {
                User user = User.getUser(target);
                boolean inServerinfo = !user.inServerInfo();

                user.setInServerInfo(inServerinfo);

                if (inServerinfo) {
                    MessageUtils.message(target, "&6&l» &7You are now in ServerInfo");

                    if (!target.equals(sender))
                        added.add(target);
                } else {
                    MessageUtils.message(target, "&6&l» &7You are no longer in ServerInfo");

                    if (!target.equals(sender))
                        removed.add(target);
                }
            }

            if (!added.isEmpty()) {
                String displaynames = ListUtils.fromList(NameUtils.toDisplayNames(added), false, false);
                String names = ListUtils.fromList(NameUtils.toNames(added), false, false);

                MessageBuilder message = new MessageBuilder();
                message.append("&6&l» ");
                message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
                message.append("&7 " + (added.size() > 1 ? "are" : "is") + " now in ServerInfo");

                sender.sendMessage(message.build());
            }

            if (!removed.isEmpty()) {
                String displaynames = ListUtils.fromList(NameUtils.toDisplayNames(added), false, false);
                String names = ListUtils.fromList(NameUtils.toNames(added), false, false);

                MessageBuilder message = new MessageBuilder();
                message.append("&6&l» ");
                message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
                message.append("&7 " + (removed.size() > 1 ? "are" : "is") + " no longer in ServerInfo");

                sender.sendMessage(message.build());
            }
        } else {
            Runtime runtime = Runtime.getRuntime();

            int cores = bean.getAvailableProcessors();

            double totalLoad = bean.getSystemCpuLoad();
            double serverLoad = bean.getProcessCpuLoad();
            String totalUsage = StringUtils.shortenNumber(totalLoad * 100, 2) + "%";
            String serverUsage = StringUtils.shortenNumber(serverLoad * 100, 2) + "%";

            if (totalLoad == 0)
                totalUsage = "Idle";
            else if (totalLoad == -1)
                totalUsage = "Unavailable";

            if (serverLoad == 0)
                serverUsage = "Idle";
            else if (serverLoad == -1)
                serverUsage = "Unavailable";

            int chunks = 0;
            int entities = 0;

            for (World world : Bukkit.getWorlds()) {
                chunks += world.getLoadedChunks().length;
                entities += world.getEntities().size();
            }

            double tps = TpsUtils.getTPS();

            double memory = runtime.maxMemory() / 1024/ 1024;
            double allocated = runtime.totalMemory() / 1024 / 1024;
            double free = runtime.freeMemory() / 1024 / 1024;
            double used = allocated - free;

            String memoryFormat = formatter.format(memory);
            String allocatedFormat = formatter.format(allocated);
            String freeFormat = formatter.format(free);
            String usedFormat = formatter.format(used);

            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» &7TPS: &6" + tps);
            message.append("\n&6&l» &7Memory: &6" + usedFormat + "&7/&6" + allocatedFormat + " &7MB (&6" + memoryFormat + "&7 MB max)").hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + freeFormat + " &7MB free");
            message.append("\n&6&l» &7Total CPU Usage: &6" + totalUsage + "&7 Server CPU Usage: &6" + serverUsage).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&7CPU Cores: &6" + cores);
            message.append("\n&6&l» &7Loaded Chunks: &6" + chunks + "&7 Entities: &6" + entities);

            MessageUtils.message(sender, message.build());
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
