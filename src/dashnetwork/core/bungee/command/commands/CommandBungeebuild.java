package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.MessageUtils;
import dashnetwork.core.bungee.utils.Messages;
import dashnetwork.core.bungee.utils.PermissionType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

public class CommandBungeebuild extends CoreCommand {

    private static SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

    public CommandBungeebuild() {
        super(true, PermissionType.OWNER, "bungeebuild");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length > 0) {
            Plugin target = bungee.getPluginManager().getPlugin(args[0]);

            if (target == null) {
                MessageUtils.message(sender, "&6&l» &cCouldn't find &6" + args[0]);
                return;
            }

            File file = target.getFile(); // Why can't bukkit have this?

            try {
                BasicFileAttributes attributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                String date = formatter.format(new Date(attributes.lastModifiedTime().toMillis()));

                MessageUtils.message(sender, "&6&l» &6" + target.getDescription().getName() + " &7jar modified on &6" + date);
            } catch (Exception exception) {
                Messages.exception(sender, exception);
                exception.printStackTrace();
            }
        } else
            MessageUtils.message(sender, "&6&l» &7/bukkitbuild <plugin>");
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
