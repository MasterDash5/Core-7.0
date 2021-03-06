package xyz.dashnetwork.core.bukkit.command.commands;

import com.sun.management.OperatingSystemMXBean;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import xyz.dashnetwork.core.bukkit.command.CoreCommand;
import xyz.dashnetwork.core.bukkit.utils.MessageUtils;
import xyz.dashnetwork.core.bukkit.utils.PermissionType;
import xyz.dashnetwork.core.bukkit.utils.TpsUtils;
import xyz.dashnetwork.core.utils.MessageBuilder;
import xyz.dashnetwork.core.utils.StringUtils;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.util.Collections;

public class CommandServerinfo extends CoreCommand {

    private OperatingSystemMXBean bean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
    private DecimalFormat formatter = new DecimalFormat("#######.##");

    public CommandServerinfo() {
        super(true, PermissionType.ADMIN, "serverinfo");
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
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

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
