package dashnetwork.core.command;

import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public abstract class CoreCommand extends Command implements TabExecutor {

    private boolean async;
    private PermissionType permission;

    public CoreCommand(boolean async, PermissionType permission, String label, String... aliases) {
        super(label, null, aliases);

        this.async = async;
        this.permission = permission;
    }

    public abstract void onCommand(CommandSender sender, String[] args);

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (permission.hasPermission(sender)) {
            if (async) {
                new Thread(() -> {
                    onCommand(sender, args);
                }).start();
            } else
                onCommand(sender, args);
        } else
            MessageUtils.noPermissions(sender);
    }

}
