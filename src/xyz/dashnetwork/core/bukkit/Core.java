package xyz.dashnetwork.core.bukkit;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.scheduler.BukkitScheduler;
import xyz.dashnetwork.core.bukkit.command.commands.*;
import xyz.dashnetwork.core.bukkit.listeners.*;
import xyz.dashnetwork.core.bukkit.tasks.SpectateTask;
import xyz.dashnetwork.core.bukkit.tasks.SpinTask;
import xyz.dashnetwork.core.bukkit.tasks.UserTask;
import xyz.dashnetwork.core.bukkit.utils.TpsUtils;
import xyz.dashnetwork.core.bukkit.utils.User;

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

        getConfig().options().copyDefaults(true);
        saveConfig();

        Messenger messenger = getServer().getMessenger();
        messenger.registerOutgoingPluginChannel(this, "dn:broadcast");
        messenger.registerIncomingPluginChannel(this, "dn:displayname", channelListener);
        messenger.registerIncomingPluginChannel(this, "dn:vanish", channelListener);
        messenger.registerIncomingPluginChannel(this, "dn:signspy", channelListener);

        TpsUtils.startup();

        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new JoinListener(), this);
        manager.registerEvents(new LoginListener(), this);
        manager.registerEvents(new PingListener(), this);
        manager.registerEvents(new QuitListener(), this);
        manager.registerEvents(new SignListener(), this);

        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.runTaskTimer(this, new SpinTask(), 2, 2);
        scheduler.runTaskTimer(this, new SpectateTask(), 1, 1);
        scheduler.runTaskTimerAsynchronously(this, new UserTask(), 1, 1);

        new CommandAnvil();
        new CommandBukkitbuild();
        new CommandCenter();
        new CommandClearlag();
        new CommandConsole();
        new CommandKillears();
        new CommandMommy();
        new CommandNightvision();
        new CommandOplist();
        new CommandRespawn();
        new CommandServerinfo();
        new CommandSpin();

        if (!manager.isPluginEnabled("Essentials")) {
            new CommandFly();
            new CommandGamemode();
        }
    }

    @Override
    public void onDisable() {
        for (User user : User.getUsers(false))
            user.remove();
    }

}
