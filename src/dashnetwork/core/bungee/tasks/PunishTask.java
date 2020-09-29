package dashnetwork.core.bungee.tasks;

import dashnetwork.core.bungee.utils.DataUtils;
import dashnetwork.core.utils.PunishData;

import java.util.Map;

public class PunishTask implements Runnable {

    @Override
    public void run() {
        Map<String, PunishData> mutes = DataUtils.getMutes();
        Map<String, PunishData> bans = DataUtils.getBans();
        Map<String, PunishData> ipbans = DataUtils.getIpbans();

        for (Map.Entry<String, PunishData> entry : mutes.entrySet())
            if (entry.getValue().isExpired())
                mutes.remove(entry.getKey());

        for (Map.Entry<String, PunishData> entry : bans.entrySet())
            if (entry.getValue().isExpired())
                bans.remove(entry.getKey());

        for (Map.Entry<String, PunishData> entry : ipbans.entrySet())
            if (entry.getValue().isExpired())
                ipbans.remove(entry.getKey());
    }

}
