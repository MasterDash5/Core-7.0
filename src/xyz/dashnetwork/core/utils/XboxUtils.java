package xyz.dashnetwork.core.utils;

import com.google.common.io.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class XboxUtils {

    private static String key;
    private static List<XboxProfile> cache;

    static {
        try {
            URL resource = XboxUtils.class.getClassLoader().getResource("apis/xapi.key").toURI().toURL();
            key = Resources.toString(resource, StandardCharsets.UTF_8);
        } catch (Exception exception) {}

        cache = new ArrayList<>();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                cache.clear();
            }
        }, 10800000, 10800000); // 3 hours. Its VERY rate limited
    }

    public static XboxProfile fromGamertag(String gamertag) {
        for (XboxProfile profile : cache)
            if (profile.getGamertag().equalsIgnoreCase(gamertag))
                return profile;

        try {
            URL url = new URL("https://xapi.us/v2/xuid/" + gamertag);
            URLConnection connection = url.openConnection();
            
            connection.setRequestProperty("X-Auth", key);
            connection.setDoOutput(true);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            long xuid = Long.parseLong(reader.readLine());

            reader.close();

            XboxProfile profile = new XboxProfile(gamertag, xuid);
            cache.add(profile);

            return profile;
        } catch (IOException exception) {}

        return null;
    }

    public static XboxProfile fromXUID(long xuid) {
        for (XboxProfile profile : cache)
            if (profile.getXUID() == xuid)
                return profile;

        try {
            URL url = new URL("https://xapi.us/v2/gamertag/" + xuid);
            URLConnection connection = url.openConnection();

            connection.setRequestProperty("X-Auth", key);
            connection.setDoOutput(true);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String gamertag = reader.readLine();

            reader.close();

            XboxProfile profile = new XboxProfile(gamertag, xuid);
            cache.add(profile);

            return profile;
        } catch (IOException exception) {}

        return null;
    }

}
