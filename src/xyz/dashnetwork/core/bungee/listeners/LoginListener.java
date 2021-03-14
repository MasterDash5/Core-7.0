package xyz.dashnetwork.core.bungee.listeners;

import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import xyz.dashnetwork.core.bungee.utils.DataUtils;
import xyz.dashnetwork.core.utils.MessageBuilder;
import xyz.dashnetwork.core.utils.PunishData;
import xyz.dashnetwork.core.utils.TimeUtils;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Map;

public class LoginListener implements Listener {

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
            expire = data.isPermanent() ? "never" : TimeUtils.TIME_FORMAT.format(new Date(data.getExpire()));
        } else if (ipbans.containsKey(address)) {
            PunishData data = ipbans.get(address);

            if (data.isExpired())
                return;

            banner = data.getBanner();
            reason = data.getReason();
            expire = data.isPermanent() ? "never" : TimeUtils.TIME_FORMAT.format(new Date(data.getExpire()));
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

}
