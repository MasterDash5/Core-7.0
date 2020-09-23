package dashnetwork.core.bungee.listeners;

import dashnetwork.core.bungee.Core;
import dashnetwork.core.bungee.utils.*;
import dashnetwork.core.utils.ListUtils;
import dashnetwork.core.utils.MessageBuilder;
import dashnetwork.core.utils.PunishData;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class LoginListener implements Listener {

    private BungeeCord bungee = BungeeCord.getInstance();
    private Core plugin = Core.getInstance();

    @EventHandler
    public void onLogin(LoginEvent event) {
        PendingConnection connection = event.getConnection();
        String uuid = connection.getUniqueId().toString();
        String address = ((InetSocketAddress) connection.getSocketAddress()).getAddress().getHostAddress();
        Map<String, PunishData> bans = DataUtils.getBans();
        Map<String, PunishData> ipbans = DataUtils.getIpbans();

        String banner;
        String reason;
        String expire;

        if (bans.containsKey(uuid)) {
            PunishData data = bans.get(uuid);

            if (data.isExpired())
                return;

            banner = data.getBanner();
            reason = data.getReason();
            expire = data.isPermanent() ? "never" : PunishUtils.getDateFormat().format(new Date(data.getExpire()));
        } else if (ipbans.containsKey(address)) {
            PunishData data = ipbans.get(address);

            if (data.isExpired())
                return;

            banner = data.getBanner();
            reason = data.getReason();
            expire = data.isPermanent() ? "never" : PunishUtils.getDateFormat().format(new Date(data.getExpire()));
        } else
            return;

        MessageBuilder message = new MessageBuilder();
        message.append("&7You have been banned from &6&lDashNetwork\n\n");
        message.append("&7Banned by &6" + banner);
        message.append("\n&7Expires &6" + expire);
        message.append("\n&7For &6" + reason);

        event.setCancelled(true);
        event.setCancelReason(message.build());
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();
        User user = User.getUser(player);
        String uuid = player.getUniqueId().toString();
        String name = player.getName();
        String displayname = user.getDisplayName();
        String address = ((InetSocketAddress) player.getSocketAddress()).getAddress().getHostAddress();

        Map<String, List<String>> ips = DataUtils.getIps();
        Map<String, String> names = DataUtils.getNames();
        List<String> uuids = ips.getOrDefault(address, new ArrayList<>());

        if (!uuids.contains(uuid))
            uuids.add(uuid);

        names.put(player.getUniqueId().toString(), player.getName());
        ips.put(address, uuids);

        bungee.getScheduler().runAsync(plugin, () -> {
            List<String> alts = new ArrayList<>();

            for (String account : uuids)
                if (!account.equals(uuid))
                    alts.add(names.get(account));

            if (!alts.isEmpty()) {
                MessageBuilder message = new MessageBuilder();
                message.append("&c&lAlt &6" + displayname + " &c&l>&7 hover for list of &6" + alts.size() + " alts").hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + ListUtils.fromList(alts, false, true));

                for (User online : User.getUsers(true))
                    if (online.inAltSpy())
                        MessageUtils.message(online, message.build());
            }
        });

        MessageBuilder message = new MessageBuilder();
        message.append("&6&l» ");
        message.append("&e" + displayname).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + name);
        message.append("&6 joined the server.");

        MessageUtils.broadcast(PermissionType.NONE, message.build());
    }

}
