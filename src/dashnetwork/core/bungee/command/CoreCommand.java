package dashnetwork.core.bungee.command;

import dashnetwork.core.bungee.Core;
import dashnetwork.core.bungee.utils.MessageUtils;
import dashnetwork.core.bungee.utils.Messages;
import dashnetwork.core.bungee.utils.PermissionType;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class CoreCommand extends Command implements TabExecutor {

    protected static BungeeCord bungee = BungeeCord.getInstance();
    protected static Core plugin = Core.getInstance();
    private boolean async;
    private PermissionType permission;

    public CoreCommand(boolean async, PermissionType permission, String label, String... aliases) {
        super(label, permission.toPermission(), addAliases(label, aliases));

        this.async = async;
        this.permission = permission;
    }

    public abstract void onCommand(CommandSender sender, String[] args);

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (permission.hasPermission(sender)) {
            if (async)
                bungee.getScheduler().runAsync(plugin, () -> onCommand(sender, args));
            else
                onCommand(sender, args);
        } else
            Messages.noPermissions(sender);
    }

    private static String[] addAliases(String label, String[] aliases) {
        List<String> list = new CopyOnWriteArrayList<>();
        list.add(label);
        list.addAll(Arrays.asList(aliases));

        for (String entry : list) {
            list.add("c" + entry);
            list.add("core:" + entry);
            list.add("core:c" + entry);
        }

        return list.toArray(new String[list.size()]);
    }

}
