package dashnetwork.core.utils;

import java.util.UUID;

public class PlayerProfile {

    private UUID uuid;
    private String name;
    private String skin;

    public PlayerProfile(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.skin = null;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

}
