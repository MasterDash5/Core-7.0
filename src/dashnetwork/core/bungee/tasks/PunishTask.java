package dashnetwork.core.bungee.tasks;

import dashnetwork.core.bungee.utils.DataUtils;
import dashnetwork.core.utils.PunishData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PunishTask implements Runnable {

    @Override
    public void run() {
        Map<String, PunishData> mutes = DataUtils.getMutes();
        Map<String, PunishData> bans = DataUtils.getBans();
        Map<String, PunishData> ipbans = DataUtils.getIpbans();

        List<String> muteRemoveQueue = new ArrayList<>();
        List<String> banRemoveQueue = new ArrayList<>();
        List<String> ipbanRemoveQueue = new ArrayList<>();

        for (Map.Entry<String, PunishData> entry : mutes.entrySet())
            if (entry.getValue().isExpired())
                muteRemoveQueue.add(entry.getKey());

        for (Map.Entry<String, PunishData> entry : bans.entrySet())
            if (entry.getValue().isExpired())
                banRemoveQueue.add(entry.getKey());

        for (Map.Entry<String, PunishData> entry : ipbans.entrySet())
            if (entry.getValue().isExpired())
                ipbanRemoveQueue.add(entry.getKey());

        for (String queue : muteRemoveQueue)
            mutes.remove(queue);

        for (String queue : banRemoveQueue)
            bans.remove(queue);

        for (String queue : ipbanRemoveQueue)
            ipbans.remove(queue);
    }

}
