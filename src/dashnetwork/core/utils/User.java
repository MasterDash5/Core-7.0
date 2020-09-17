package dashnetwork.core.utils;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class User implements CommandSender {

    private static List<User> users = new CopyOnWriteArrayList<>();
    private static BungeeCord bungee = BungeeCord.getInstance();
    private ProxiedPlayer player;
    private boolean staffChat;
    private boolean adminChat;
    private boolean ownerChat;
    private boolean commandSpy;
    private boolean altSpy;
    private boolean pingSpy;

    private User(ProxiedPlayer player) {
        this.player = player;
        this.staffChat = false;
        this.adminChat = false;
        this.ownerChat = false;
        this.commandSpy = false;
        this.altSpy = false;
        this.pingSpy = false;

        loadSaves();

        users.add(this);
    }

    public static List<User> getUsers() {
        for (ProxiedPlayer online : bungee.getPlayers())
            User.getUser(online);
        return users;
    }

    public static User getUser(ProxiedPlayer player) {
        for (User user : users)
            if (user.getPlayer().equals(player))
                return user;
        return new User(player);
    }

    public static boolean hasInstance(ProxiedPlayer player) {
        for (User user : users)
            if (user.getPlayer().equals(player))
                return true;
        return false;
    }

    public void loadSaves() {
        String uuid = player.getUniqueId().toString();

        if (DataUtils.getStaffchat().contains(uuid))
            this.staffChat = true;

        if (DataUtils.getAdminchat().contains(uuid))
            this.adminChat = true;

        if (DataUtils.getOwnerchat().contains(uuid))
            this.ownerChat = true;

        if (DataUtils.getCommandspy().contains(uuid))
            this.commandSpy = true;

        if (DataUtils.getAltspy().contains(uuid))
            this.altSpy = true;

        if (DataUtils.getPingspy().contains(uuid))
            this.pingSpy = true;
    }

    public void save() {
        String uuid = player.getUniqueId().toString();
        List<String> staffchatList = DataUtils.getStaffchat();
        List<String> adminchatList = DataUtils.getAdminchat();
        List<String> ownerchatList = DataUtils.getOwnerchat();
        List<String> commandspyList = DataUtils.getCommandspy();
        List<String> altspyList = DataUtils.getAltspy();
        List<String> pingspyList = DataUtils.getPingspy();

        if (staffChat) {
            if (!staffchatList.contains(uuid))
                staffchatList.add(uuid);
        } else
            staffchatList.remove(uuid);

        if (adminChat) {
            if (!adminchatList.contains(uuid))
                adminchatList.add(uuid);
        } else
            adminchatList.remove(uuid);

        if (ownerChat) {
            if (!ownerchatList.contains(uuid))
                ownerchatList.add(uuid);
        } else
            ownerchatList.remove(uuid);

        if (commandSpy) {
            if (!commandspyList.contains(uuid))
                commandspyList.add(uuid);
        } else
            commandspyList.remove(uuid);

        if (altSpy) {
            if (!altspyList.contains(uuid))
                altspyList.add(uuid);
        } else
            altspyList.remove(uuid);

        if (pingSpy) {
            if (!pingspyList.contains(uuid))
                pingspyList.add(uuid);
        } else
            pingspyList.remove(uuid);
    }

    public void remove() {
        save();

        users.remove(this);
    }

    public ProxiedPlayer getPlayer() {
        return player;
    }

    public boolean isStaff() {
        return hasPermission("dashnetwork.staff") || isAdmin();
    }

    public boolean isAdmin() {
        return hasPermission("dashnetwork.admin") || isOwner();
    }

    public boolean isOwner() {
        return hasPermission("dashnetwork.owner") || isDash() || isMatt();
    }

    public boolean isDash() {
        return LazyUtils.anyEquals(player.getUniqueId().toString(), "4f771152-ce61-4d6f-9541-1d2d9e725d0e", "d1e65ac2-5815-42fd-a900-51f520d286b2", "1dadf63d-c067-43ef-a49f-8428e3cecc78", "23dcf775-d9c4-40a5-8772-18f5773e1536");
    }

    public boolean isMatt() {
        return player.getUniqueId().toString().equals("0e9c49ee-ed25-462f-b7c4-48cd98a30a62");
    }

    public boolean inStaffChat() {
        return staffChat;
    }

    public void setStaffChat(boolean staffChat) {
        this.staffChat = staffChat;
    }

    public boolean inAdminChat() {
        return adminChat;
    }

    public void setAdminChat(boolean adminChat) {
        this.adminChat = adminChat;
    }

    public boolean inOwnerChat() {
        return ownerChat;
    }

    public void setOwnerChat(boolean ownerChat) {
        this.ownerChat = ownerChat;
    }

    public boolean inCommandSpy() {
        return commandSpy;
    }

    public void setCommandSpy(boolean commandSpy) {
        this.commandSpy = commandSpy;
    }

    public boolean inAltSpy() {
        return altSpy;
    }

    public void setAltSpy(boolean altspy) {
        this.altSpy = altspy;
    }

    public boolean inPingSpy() {
        return pingSpy;
    }

    public void setPingSpy(boolean pingSpy) {
        this.pingSpy = pingSpy;
    }

    public boolean allowedChatColors() {
        return isStaff();
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public void sendMessage(String message) {
        MessageUtils.message(this, message);
    }

    @Override
    public void sendMessages(String... messages) {
        for (String message : messages)
            sendMessage(message);
    }

    @Override
    public void sendMessage(BaseComponent... message) {
        player.sendMessage(message);
    }

    @Override
    public void sendMessage(BaseComponent message) {
        player.sendMessage(message);
    }

    @Override
    public Collection<String> getGroups() {
        return player.getGroups();
    }

    @Override
    public void addGroups(String... groups) {
        player.addGroups(groups);
    }

    @Override
    public void removeGroups(String... groups) {
        player.removeGroups(groups);
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public void setPermission(String permission, boolean value) {
        player.setPermission(permission, value);
    }

    @Override
    public Collection<String> getPermissions() {
        return player.getPermissions();
    }

}
