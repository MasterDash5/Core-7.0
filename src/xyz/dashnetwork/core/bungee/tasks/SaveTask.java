package xyz.dashnetwork.core.bungee.tasks;

import xyz.dashnetwork.core.bungee.utils.DataUtils;

public class SaveTask implements Runnable {

    @Override
    public void run() {
        DataUtils.save();
    }

}
