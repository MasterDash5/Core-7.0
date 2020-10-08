package dashnetwork.core.bungee.command.commands;

import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.MessageUtils;
import dashnetwork.core.bungee.utils.PermissionType;
import net.md_5.bungee.api.CommandSender;

import java.util.Collections;

public class CommandFurpysong extends CoreCommand {

    public CommandFurpysong() {
        super(true, PermissionType.NONE, "furpysong", "thefurpysong");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        String message = "&7The Furpy Song: by Skullrama's Crazy Friend" +
                "\n" +
                "\n&7(Theme from Ikuroshitsuji closing season 1)" +
                "\n" +
                "\n&6One day at my lunch, Skull told me 'bout a bunch." +
                "\nThen days after that, my life changed forever." +
                "\n" +
                "\nBut now I won't waste any time, cause something is on my mind." +
                "\nSomeone that is awesome, a 54 kid." +
                "\n" +
                "\nFur- PEE, Fur- PEE, oh yeah, Furpy my one and only, reach for the ceiling." +
                "\nFur- PEE A Fur and a Pee." +
                "\nMy one, and only Furpy." +
                "\n" +
                "\nIt doesn't matter who you are, Even if you are married and have kids, Root Beer and Panda Wilson." +
                "\nX + 1 does that matter?" +
                "\n" +
                "\nAll my friends they ran away, but you just come back and stay." +
                "\nThis love song to you." +
                "\nYeah, you 54 kid." +
                "\n" +
                "\nFur- PEE, fur- PEE, oh yeah." +
                "\nPlease don't be a girl I'd be depressed." +
                "\nReach for the ceiling My Furpy." +
                "\n" +
                "\nMy one and only Furpy, a Fur and a Pee, you are..." +
                "\nmy only..." +
                "\nyou are" +
                "\nmy true" +
                "\nFURPY!!" +
                "\n" +
                "\n&7(Applause)";

        MessageUtils.message(sender, message);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }

}
