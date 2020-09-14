package dashnetwork.core;

import dashnetwork.core.command.commands.CommandTest;
import dashnetwork.core.listeners.ChannelListener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class Core extends Plugin {

    @Override
    public void onEnable() {
        PluginManager manager = getProxy().getPluginManager();

        manager.registerListener(this, new ChannelListener());

        manager.registerCommand(this, new CommandTest());
    }

    @Override
    public void onDisable() {

    }

}
