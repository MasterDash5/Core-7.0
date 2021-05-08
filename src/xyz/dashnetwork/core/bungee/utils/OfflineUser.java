package xyz.dashnetwork.core.bungee.utils;

import xyz.dashnetwork.core.utils.PunishData;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OfflineUser {

    private static Map<String, String> names = DataUtils.getNames();
    protected String name, address, nickname;
    protected UUID uuid;
    protected long lastPlayed;
    protected boolean staffChat, adminChat, ownerChat, commandSpy, altSpy, pingSpy;

    protected OfflineUser(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.address = null;
        this.lastPlayed = -1;
        this.nickname = null;
        this.staffChat = false;
        this.adminChat = false;
        this.ownerChat = false;
        this.commandSpy = false;
        this.altSpy = false;
        this.pingSpy = false;

        loadSaves();
    }

    public static OfflineUser getOfflineUser(UUID uuid, String username) {
        for (User user : User.getUsers(true))
            if (user.getUniqueId().equals(uuid))
                return user;
        return new OfflineUser(uuid, username);
    }

    private void loadSaves() {
        String stringUuid = uuid.toString();
        Map<String, Long> lastplayed = DataUtils.getLastplayed();
        Map<String, String> nicknames = DataUtils.getNicknames();

        for (Map.Entry<String, List<String>> entry : DataUtils.getIps().entrySet()) {
            if (entry.getValue().contains(stringUuid)) {
                this.address = entry.getKey();
                break;
            }
        }

        if (lastplayed.containsKey(stringUuid))
            this.lastPlayed = lastplayed.get(stringUuid);

        if (nicknames.containsKey(stringUuid))
            this.nickname = nicknames.get(stringUuid);

        if (DataUtils.getStaffchat().contains(stringUuid))
            this.staffChat = true;

        if (DataUtils.getAdminchat().contains(stringUuid))
            this.adminChat = true;

        if (DataUtils.getOwnerchat().contains(stringUuid))
            this.ownerChat = true;

        if (DataUtils.getCommandspy().contains(stringUuid))
            this.commandSpy = true;

        if (DataUtils.getAltspy().contains(stringUuid))
            this.altSpy = true;

        if (DataUtils.getPingspy().contains(stringUuid))
            this.pingSpy = true;
    }

    public boolean isBedrock() {
        return uuid.getMostSignificantBits() == 0;
    }

    public boolean hasJoined() {
        return address != null;
    }

    public boolean isOnline() {
        return this instanceof User;
    }

    public String getName() {
        return name;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public String getAddress() {
        return address;
    }

    public long getLastPlayed() {
        return lastPlayed;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean inStaffChat() {
        return staffChat;
    }

    public boolean inAdminChat() {
        return adminChat;
    }

    public boolean inOwnerChat() {
        return ownerChat;
    }

    public boolean inCommandSpy() {
        return commandSpy;
    }

    public boolean inAltSpy() {
        return altSpy;
    }

    public boolean inPingSpy() {
        return pingSpy;
    }

    public boolean isBanned() {
        PunishData ban = DataUtils.getBans().get(uuid.toString());

        return ban != null && !ban.isExpired();
    }

    public PunishData getBan() {
        return DataUtils.getBans().get(uuid.toString());
    }

    public boolean isMuted() {
        PunishData mute = DataUtils.getMutes().get(uuid.toString());

        return mute != null && !mute.isExpired();
    }

    public PunishData getMute() {
        return DataUtils.getMutes().get(uuid.toString());
    }

}
