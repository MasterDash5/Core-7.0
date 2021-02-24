package dashnetwork.core.bungee;

import dashnetwork.core.bungee.command.commands.*;
import dashnetwork.core.bungee.listeners.*;
import dashnetwork.core.bungee.pain.Pain;
import dashnetwork.core.bungee.tasks.PunishTask;
import dashnetwork.core.bungee.tasks.SaveTask;
import dashnetwork.core.bungee.tasks.UserTask;
import dashnetwork.core.bungee.utils.DataUtils;
import dashnetwork.core.bungee.utils.User;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.api.scheduler.TaskScheduler;

import java.util.concurrent.TimeUnit;

public class Core extends Plugin {

    private static Pain pain;
    private static Core instance;

    public static Core getInstance() {
        return instance;
    }

    public static Pain getPain() {
        return pain;
    }

    @Override
    public void onEnable() {
        instance = this;

        // pain = new Pain(); TODO: Finish Pain
        // pain.start();

        DataUtils.startup();

        ProxyServer proxy = getProxy();
        TaskScheduler scheduler = proxy.getScheduler();
        PluginManager manager = proxy.getPluginManager();

        proxy.registerChannel("dn:online");
        proxy.registerChannel("dn:displayname");
        proxy.registerChannel("dn:vanish");
        proxy.registerChannel("dn:signspy");

        scheduler.schedule(this, new PunishTask(), 0, 1, TimeUnit.HOURS);
        scheduler.schedule(this, new SaveTask(), 1, 1, TimeUnit.HOURS);
        scheduler.schedule(this, new UserTask(), 50, 50, TimeUnit.MILLISECONDS); // 50 ms = 1 tick

        manager.registerListener(this, new ChannelListener());
        manager.registerListener(this, new ChatListener());
        manager.registerListener(this, new ConnectListener());
        manager.registerListener(this, new JoinListener());
        manager.registerListener(this, new KickListener());
        manager.registerListener(this, new LoginListener());
        manager.registerListener(this, new PingListener());
        manager.registerListener(this, new QuitListener());

        manager.registerCommand(this, new CommandAdminchat());
        manager.registerCommand(this, new CommandAltlist());
        manager.registerCommand(this, new CommandAltspy());
        manager.registerCommand(this, new CommandBan());
        manager.registerCommand(this, new CommandBungeebuild());
        manager.registerCommand(this, new CommandChat());
        manager.registerCommand(this, new CommandChatsudo());
        manager.registerCommand(this, new CommandClearchat());
        manager.registerCommand(this, new CommandColorlist());
        manager.registerCommand(this, new CommandCommandspy());
        manager.registerCommand(this, new CommandCreative());
        manager.registerCommand(this, new CommandDiscord());
        manager.registerCommand(this, new CommandFakejoin());
        manager.registerCommand(this, new CommandFakeleave());
        manager.registerCommand(this, new CommandFurpysong());
        manager.registerCommand(this, new CommandIpban());
        manager.registerCommand(this, new CommandKick());
        manager.registerCommand(this, new CommandList());
        manager.registerCommand(this, new CommandLobby());
        manager.registerCommand(this, new CommandLocalchat());
        manager.registerCommand(this, new CommandMattsarmorstands());
        manager.registerCommand(this, new CommandMessage());
        manager.registerCommand(this, new CommandMute());
        manager.registerCommand(this, new CommandNickname());
        manager.registerCommand(this, new CommandOwnerchat());
        manager.registerCommand(this, new CommandPing());
        manager.registerCommand(this, new CommandPingspy());
        manager.registerCommand(this, new CommandPlayerinfo());
        manager.registerCommand(this, new CommandPvp());
        manager.registerCommand(this, new CommandReply());
        manager.registerCommand(this, new CommandServer());
        manager.registerCommand(this, new CommandSignspy());
        manager.registerCommand(this, new CommandSkyblock());
        manager.registerCommand(this, new CommandStaff());
        manager.registerCommand(this, new CommandStaffchat());
        manager.registerCommand(this, new CommandSurvival());
        manager.registerCommand(this, new CommandTempban());
        manager.registerCommand(this, new CommandTempipban());
        manager.registerCommand(this, new CommandTempmute());
        manager.registerCommand(this, new CommandUnban());
        manager.registerCommand(this, new CommandUnipban());
        manager.registerCommand(this, new CommandUnmute());
        manager.registerCommand(this, new CommandUuidconvert());
        manager.registerCommand(this, new CommandVanish());
        manager.registerCommand(this, new CommandVersionlist());
    }

    @Override
    public void onDisable() {
        for (User user : User.getUsers(false))
            user.remove();

        pain.stop();

        DataUtils.save();
    }

}
