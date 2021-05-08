package xyz.dashnetwork.core.utils;

import java.util.UUID;

public class XboxProfile {

    private String gamertag;
    private long xuid;

    public XboxProfile(String gamertag, long xuid) {
        this.gamertag = gamertag;
        this.xuid = xuid;
    }

    public String getGamertag() {
        return gamertag;
    }

    public long getXUID() {
        return xuid;
    }

    public UUID getUUID() {
        return new UUID(0, xuid);
    }

}
