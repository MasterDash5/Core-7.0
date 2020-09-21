package dashnetwork.core.bungee;

import dashnetwork.core.bungee.command.commands.*;
import dashnetwork.core.bungee.listeners.*;
import dashnetwork.core.bungee.utils.DataUtils;
import dashnetwork.core.bungee.utils.User;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class Core extends Plugin {

    private static Core instance;

    public static Core getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        DataUtils.startup();

        PluginManager manager = getProxy().getPluginManager();

        manager.registerListener(this, new ChannelListener());
        manager.registerListener(this, new ChatListener());
        manager.registerListener(this, new LoginListener());
        manager.registerListener(this, new PingListener());
        manager.registerListener(this, new QuitListener());

        manager.registerCommand(this, new CommandAdminchat());
        manager.registerCommand(this, new CommandAltlist());
        manager.registerCommand(this, new CommandAltspy());
        manager.registerCommand(this, new CommandCommandspy());
        manager.registerCommand(this, new CommandIpban());
        manager.registerCommand(this, new CommandKick());
        manager.registerCommand(this, new CommandLobby());
        manager.registerCommand(this, new CommandLocalchat());
        manager.registerCommand(this, new CommandMute());
        manager.registerCommand(this, new CommandOwnerchat());
        manager.registerCommand(this, new CommandPingspy());
        manager.registerCommand(this, new CommandStaffchat());
        manager.registerCommand(this, new CommandTempban());
        manager.registerCommand(this, new CommandTempipban());
        manager.registerCommand(this, new CommandTempmute());
        manager.registerCommand(this, new CommandTest());
        manager.registerCommand(this, new CommandVersionlist());
    }

    @Override
    public void onDisable() {
        for (User user : User.getUsers())
            user.remove();

        DataUtils.save();
    }

}
