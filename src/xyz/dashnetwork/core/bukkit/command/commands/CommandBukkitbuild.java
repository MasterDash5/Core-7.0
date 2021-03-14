package xyz.dashnetwork.core.bukkit.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.dashnetwork.core.bukkit.command.CoreCommand;
import xyz.dashnetwork.core.bukkit.utils.MessageUtils;
import xyz.dashnetwork.core.bukkit.utils.PermissionType;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

public class CommandBukkitbuild extends CoreCommand {

    private static SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

    public CommandBukkitbuild() {
        super(true, PermissionType.OWNER, "bukkitbuild");
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (args.length > 0) {
            JavaPlugin target = (JavaPlugin) Bukkit.getPluginManager().getPlugin(args[0]);

            if (target == null) {
                MessageUtils.message(sender, "&6&l» &cCouldn't find &6" + args[0]);
                return;
            }

            try {
                Method method = JavaPlugin.class.getDeclaredMethod("getFile");
                method.setAccessible(true);

                File file = (File) method.invoke(target);
                BasicFileAttributes attributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                String date = formatter.format(new Date(attributes.lastModifiedTime().toMillis()));

                MessageUtils.message(sender, "&6&l» &6" + target.getName() + " &7jar modified on &6" + date);
            } catch (Exception exception) {
                MessageUtils.sendException(sender, exception);
                exception.printStackTrace();
            }
        } else
            MessageUtils.message(sender, "&6&l» &7/bukkitbuild <plugin>");
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
