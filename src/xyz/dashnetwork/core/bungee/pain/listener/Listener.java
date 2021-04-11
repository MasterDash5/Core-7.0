package xyz.dashnetwork.core.bungee.pain.listener;

import java.util.ArrayList;
import java.util.List;

public abstract class Listener implements Runnable {

    public static List<Listener> listeners = new ArrayList<>();
    private Thread thread;
    private boolean running;

    public Listener() {
        thread = new Thread(this, "Pain - " + getClass().getSimpleName());
        running = false;

        listeners.add(this);
    }

    public static void stopAll() {
        for (Listener listener : listeners)
            if (listener.isRunning())
                listener.stop();
        listeners.clear();
    }

    public void start() {
        running = true;
        thread.start();
    }

    public void stop() {
        running = false;
        thread.interrupt();
    }

    public boolean isRunning() {
        return running;
    }

    public abstract void listen();

    @Override
    public void run() {
        while (running)
            listen();
    }

}
