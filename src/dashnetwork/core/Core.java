package dashnetwork.core;

import dashnetwork.core.command.commands.*;
import dashnetwork.core.listeners.ChannelListener;
import dashnetwork.core.listeners.ChatListener;
import dashnetwork.core.listeners.PingListener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class Core extends Plugin {

    @Override
    public void onEnable() {
        PluginManager manager = getProxy().getPluginManager();

        manager.registerListener(this, new ChannelListener());
        manager.registerListener(this, new ChatListener());
        manager.registerListener(this, new PingListener());

        manager.registerCommand(this, new CommandAdminchat());
        manager.registerCommand(this, new CommandCommandspy());
        manager.registerCommand(this, new CommandStaffchat());
        manager.registerCommand(this, new CommandOwnerchat());
        manager.registerCommand(this, new CommandTest());
    }

    @Override
    public void onDisable() {

    }

}
