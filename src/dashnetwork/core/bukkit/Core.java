package dashnetwork.core.bukkit;

import dashnetwork.core.bukkit.command.commands.CommandServerinfo;
import dashnetwork.core.bukkit.utils.TpsUtils;
import dashnetwork.core.bukkit.utils.User;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {

    private static Core instance;

    public static Core getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        new CommandServerinfo();

        TpsUtils.startup();

        getServer().getMessenger().registerOutgoingPluginChannel(this, "dashnetwork:broadcast");
    }

    @Override
    public void onDisable() {
        for (User user : User.getUsers())
            user.remove();
    }

}
