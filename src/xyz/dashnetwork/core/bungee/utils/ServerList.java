package xyz.dashnetwork.core.bungee.utils;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import xyz.dashnetwork.core.bungee.Core;
import xyz.dashnetwork.core.utils.LazyUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ServerList {

    private static List<Server> servers = new ArrayList<>();
    private File file = new File(Core.getInstance().getDataFolder(), "servers.yml");
    private Configuration config;

    public static List<Server> getServers() {
        return servers;
    }

    public static Server getServer(String name) {
        for (Server server : servers)
            if (server.getName().equalsIgnoreCase(name) || LazyUtils.anyEqualsIgnoreCase(name, server.getAliases()))
                return server;
        return null;
    }

    public static Server getServer(ServerInfo info) {
        for (Server server : servers)
            if (server.getServerInfo().equals(info))
                return server;
        return null;
    }

    public void load() {
        try {
            if (!file.exists())
                Files.copy(Core.class.getClassLoader().getResourceAsStream("servers.yml"), file.toPath());

            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void update() {
        servers.clear();

        for (String key : config.getKeys()) {
            List<String> list = new ArrayList<>();
            boolean bedrock = true;

            if (config.contains(key + ".aliases"))
                list = config.getStringList(key + ".aliases");

            if (config.contains(key + ".bedrock"))
                bedrock = config.getBoolean(key + ".bedrock");

            String name = config.getString(key + ".name");
            String[] aliases = list.toArray(new String[list.size()]);
            int version = config.getInt(key + ".version");
            PermissionType permission = PermissionType.valueOf(config.getString(key + ".permission"));

            servers.add(new Server(name, aliases, version, permission, bedrock));
        }

        Collections.sort(servers, Comparator.comparing(Server::getName));
    }

}
