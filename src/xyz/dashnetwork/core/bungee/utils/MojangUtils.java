package xyz.dashnetwork.core.bungee.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import xyz.dashnetwork.core.utils.PlayerProfile;
import xyz.dashnetwork.core.utils.Username;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class MojangUtils {

    // Not technically cache, but we have ram to eat anyway.
    private static Map<String, PlayerProfile> uuidCache, profileCache;
    private static Map<String, Username[]> usernameCache;

    static {
        uuidCache = new HashMap<>();
        usernameCache = new HashMap<>();
        profileCache = new HashMap<>();
    }

    public static PlayerProfile getUuidFromName(String username) {
        final String lowercase = username.toLowerCase();

        if (!uuidCache.containsKey(lowercase)) {
            try {
                URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                JsonObject json = new JsonParser().parse(reader).getAsJsonObject();

                String name = json.get("name").getAsString();
                UUID uuid = UUID.fromString(json.get("id").getAsString().replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"));

                uuidCache.put(lowercase, new PlayerProfile(uuid, name));

                reader.close();

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        uuidCache.remove(lowercase);
                    }
                }, 600000); // 10 minute rate limit
            } catch (Exception exception) {
                return null;
            }
        }

        return uuidCache.get(lowercase);
    }

    public static Username[] getNameHistoryFromUuid(UUID uuid) {
        String stringUuid = uuid.toString();

        if (!usernameCache.containsKey(stringUuid)) {
            try {
                URL url = new URL("https://api.mojang.com/user/profiles/" + stringUuid + "/names");
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                JsonArray json = new JsonParser().parse(reader).getAsJsonArray();

                List<Username> usernames = new ArrayList<>();
                int size = json.size();

                for (int i = 0; i < size; i++) {
                    JsonObject object = json.get(i).getAsJsonObject();
                    String name = object.get("name").getAsString();
                    long date = -1;

                    if (object.has("changedToAt"))
                        date = object.get("changedToAt").getAsLong();

                    usernames.add(new Username(name, date));
                }

                usernameCache.put(stringUuid, usernames.toArray(new Username[size - 1]));

                reader.close();

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        usernameCache.remove(stringUuid);
                    }
                }, 600000); // 10 minute rate limit
            } catch (Exception exception) {
                return null;
            }
        }

        return usernameCache.get(stringUuid);
    }

    public static PlayerProfile getProfileFromUuid(UUID uuid) {
        String stringUuid = uuid.toString();

        if (!profileCache.containsKey(stringUuid)) {
            try {
                URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + stringUuid);
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                JsonObject json = new JsonParser().parse(reader).getAsJsonObject();

                String name = json.get("name").getAsString();

                PlayerProfile profile = new PlayerProfile(uuid, name);

                if (json.has("properties")) {
                    JsonArray properties = json.get("properties").getAsJsonArray();

                    if (properties.size() > 0)
                        profile.setSkin(properties.get(0).getAsJsonObject().get("value").getAsString());
                }

                profileCache.put(stringUuid, profile);

                reader.close();

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        profileCache.remove(stringUuid);
                    }
                }, 60000); // 1 minute rate limit
            } catch (Exception exception) {
                return null;
            }
        }

        return profileCache.get(stringUuid);
    }

}
