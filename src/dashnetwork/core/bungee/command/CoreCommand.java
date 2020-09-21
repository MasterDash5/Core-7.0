package dashnetwork.core.bungee.command;

import dashnetwork.core.bungee.Core;
import dashnetwork.core.bungee.utils.MessageUtils;
import dashnetwork.core.bungee.utils.PermissionType;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public abstract class CoreCommand extends Command implements TabExecutor {

    protected static BungeeCord bungee = BungeeCord.getInstance();
    protected static Core plugin = Core.getInstance();
    private boolean async;
    private PermissionType permission;

    public CoreCommand(boolean async, PermissionType permission, String label, String... aliases) {
        super(label, permission.toPermission(), aliases);

        this.async = async;
        this.permission = permission;
    }

    public abstract void onCommand(CommandSender sender, String[] args);

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (permission.hasPermission(sender)) {
            if (async) {
                bungee.getScheduler().runAsync(plugin, () -> onCommand(sender, args));
            } else
                onCommand(sender, args);
        } else
            MessageUtils.noPermissions(sender);
    }

}
