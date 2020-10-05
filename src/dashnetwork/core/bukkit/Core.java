package dashnetwork.core.bukkit;

import dashnetwork.core.bukkit.command.commands.*;
import dashnetwork.core.bukkit.listeners.*;
import dashnetwork.core.bukkit.tasks.UserTask;
import dashnetwork.core.bukkit.utils.TpsUtils;
import dashnetwork.core.bukkit.utils.User;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.scheduler.BukkitScheduler;

public class Core extends JavaPlugin {

    private static Core instance;
    private static ChannelListener channelListener;

    public static Core getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        channelListener = new ChannelListener();

        Messenger messenger = getServer().getMessenger();
        messenger.registerOutgoingPluginChannel(this, "dn:broadcast");
        messenger.registerIncomingPluginChannel(this, "dn:displayname", channelListener);
        messenger.registerIncomingPluginChannel(this, "dn:vanish", channelListener);
        messenger.registerIncomingPluginChannel(this, "dn:bookspy", channelListener);
        messenger.registerIncomingPluginChannel(this, "dn:signspy", channelListener);

        TpsUtils.startup();

        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new BookListener(), this);
        manager.registerEvents(new JoinListener(), this);
        manager.registerEvents(new LoginListener(), this);
        manager.registerEvents(new QuitListener(), this);

        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.runTaskTimerAsynchronously(this, new UserTask(), 1, 1);

        new CommandAnvil();
        new CommandClearlag();
        new CommandConsole();
        new CommandMommy();
        new CommandNightvision();
        new CommandServerinfo();
    }

    @Override
    public void onDisable() {
        for (User user : User.getUsers(false))
            user.remove();
    }

}
