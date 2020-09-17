package dashnetwork.core.bukkit.command;

import dashnetwork.core.bukkit.Core;
import dashnetwork.core.bukkit.utils.MessageUtils;
import dashnetwork.core.bukkit.utils.PermissionType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class CoreCommand implements CommandExecutor {

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

}
