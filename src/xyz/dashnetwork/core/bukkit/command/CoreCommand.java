package xyz.dashnetwork.core.bukkit.command;

import com.google.common.collect.Lists;
import org.bukkit.command.*;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dashnetwork.core.bukkit.Core;
import xyz.dashnetwork.core.bukkit.utils.MessageUtils;
import xyz.dashnetwork.core.bukkit.utils.PermissionType;

import java.util.Collections;
import java.util.List;

public abstract class CoreCommand implements CommandExecutor, TabCompleter {

    protected static Core plugin = Core.getInstance();
    private boolean async;
    private PermissionType permission;

    public CoreCommand(boolean async, PermissionType permission, String label) {
        PluginCommand command = plugin.getCommand(label);
        command.setExecutor(this);
        command.setPermission(permission.toPermission());

        this.async = async;
        this.permission = permission;
    }

    public abstract void onCommand(CommandSender sender, String label, String[] args);

    public abstract Iterable<String> onTabComplete(CommandSender sender, String label, String[] args);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (permission.hasPermission(sender)) {
            if (async) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        onCommand(sender, label, args);
                    }
                }.runTaskAsynchronously(plugin);
            } else
                onCommand(sender, label, args);
        } else
            MessageUtils.noPermissions(sender);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (permission.hasPermission(sender))
            return Lists.newArrayList(onTabComplete(sender, label, args));
        return Collections.EMPTY_LIST;
    }

}
