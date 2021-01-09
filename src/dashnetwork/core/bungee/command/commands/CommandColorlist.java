package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.MessageUtils;
import dashnetwork.core.bungee.utils.PermissionType;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.Collections;

public class CommandColorlist extends CoreCommand {

    public CommandColorlist() {
        super(true, PermissionType.NONE, "colorlist", "colors");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        String message = "#6#l» #1&1 #2&2 #3&3 #4&4 #5&5 #6&6 #7&7 #8&8 #9&9 #0&0 #a&a #b&b #c&c #d&d #e&e #f&f"
                + "\n#6#l»#f #k&k#f(&k) #l&l#f #m&m#f #n&n#f #o&o";

        MessageUtils.message(sender, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('#', message)));
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
