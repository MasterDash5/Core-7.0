package dashnetwork.core.bukkit.command.commands;

import dashnetwork.core.bukkit.command.CoreCommand;
import dashnetwork.core.bukkit.utils.MessageUtils;
import dashnetwork.core.bukkit.utils.PermissionType;
import dashnetwork.core.utils.ListUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandOplist extends CoreCommand {

    public CommandOplist() {
        super(true, PermissionType.OWNER, "oplist");
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        List<String> operators = new ArrayList<>();

        for (OfflinePlayer operator : Bukkit.getOperators())
            operators.add(operator.getName());

        MessageUtils.message(sender, "&6&l» &7Operators: &6" + ListUtils.fromList(operators, false, false));
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
