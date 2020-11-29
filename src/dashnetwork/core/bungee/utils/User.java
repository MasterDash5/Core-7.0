package dashnetwork.core.bungee.utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dashnetwork.core.bungee.Core;
import dashnetwork.core.bungee.events.UserChatEvent;
import dashnetwork.core.bungee.events.UserVanishEvent;
import dashnetwork.core.utils.*;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.PluginManager;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class User implements CommandSender {

    private static List<User> users = new CopyOnWriteArrayList<>();
    private static BungeeCord bungee = BungeeCord.getInstance();
    private static Core plugin = Core.getInstance();
    private static PluginManager pluginManager = bungee.getPluginManager();
    private static LuckPerms lp = LuckPermsProvider.get();
    private ProxiedPlayer player;
    private String replyTarget, nickname, displayName;
    private boolean staffChat, adminChat, ownerChat, localChat, commandSpy, altSpy, pingSpy, signSpy, vanished;

    private User(ProxiedPlayer player) {
        this.player = player;
        this.replyTarget = null;
        this.nickname = null;
        this.displayName = null;
        this.staffChat = false;
        this.adminChat = false;
        this.ownerChat = false;
        this.localChat = false;
        this.commandSpy = false;
        this.altSpy = false;
        this.pingSpy = false;
        this.signSpy = false;
        this.vanished = false;

        loadSaves();
        updateDisplayName(false);

        users.add(this);
    }

    public static List<User> getUsers(boolean createNew) {
        if (createNew)
            for (ProxiedPlayer online : bungee.getPlayers())
                getUser(online);
        return users;
    }

    public static User getUser(ProxiedPlayer player) {
        for (User user : users)
            if (user.getPlayer().equals(player))
                return user;
        return new User(player);
    }

    public void loadSaves() {
        String uuid = player.getUniqueId().toString();
        Map<String, String> nicknameMap = DataUtils.getNicknames();

        if (nicknameMap.containsKey(uuid))
            this.nickname = nicknameMap.get(uuid);

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
        Map<String, String> nicknameMap = DataUtils.getNicknames();
        List<String> staffchatList = DataUtils.getStaffchat();
        List<String> adminchatList = DataUtils.getAdminchat();
        List<String> ownerchatList = DataUtils.getOwnerchat();
        List<String> commandspyList = DataUtils.getCommandspy();
        List<String> altspyList = DataUtils.getAltspy();
        List<String> pingspyList = DataUtils.getPingspy();

        if (nickname != null && !nickname.equals(getName()))
            nicknameMap.put(uuid, nickname);
        else
            nicknameMap.remove(uuid);

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

    public void chat(Channel channel, String message) {
        String name = getPlayer().getName();
        String displayname = getDisplayName();
        MessageBuilder broadcast = new MessageBuilder();

        switch (channel) {
            case GLOBAL:
                broadcast.append("&6" + displayname).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + name);
                broadcast.append(" &e&l>&f " + message);

                MessageUtils.broadcast(PermissionType.NONE, broadcast.build());

                pluginManager.callEvent(new UserChatEvent(this, PermissionType.NONE, message));

                break;
            case LOCAL:
                getPlayer().chat(message); // Doesn't call ChatEvent apparently.

                break;
            case STAFF:
                broadcast.append("&9&lStaff ");
                broadcast.append("&6" + displayname).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + name);
                broadcast.append(" &6&l>&6 " + message);

                MessageUtils.broadcast(PermissionType.STAFF, broadcast.build());

                pluginManager.callEvent(new UserChatEvent(this, PermissionType.STAFF, message));

                break;
            case ADMIN:
                broadcast.append("&9&lAdmin ");
                broadcast.append("&6" + displayname).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + name);
                broadcast.append(" &6&l>&3 " + message);

                MessageUtils.broadcast(PermissionType.ADMIN, broadcast.build());

                pluginManager.callEvent(new UserChatEvent(this, PermissionType.ADMIN, message));

                break;
            case OWNER:
                broadcast.append("&9&lOwner ");
                broadcast.append("&6" + displayname).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + name);
                broadcast.append(" &6&l>&c " + message);

                MessageUtils.broadcast(PermissionType.OWNER, broadcast.build());

                pluginManager.callEvent(new UserChatEvent(this, PermissionType.OWNER, message));

                break;
        }
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

    public boolean isAbove(User user) {
        if (isOwner())
            return true;

        if (isAdmin())
            return !user.isAdmin();

        if (isStaff())
            return !user.isStaff();

        return false;
    }

    public boolean isMuted() {
        String uuid = getPlayer().getUniqueId().toString();
        Map<String, PunishData> mutes = DataUtils.getMutes();

        if (mutes.containsKey(uuid)) {
            PunishData data = mutes.get(uuid);

            if (!data.isExpired())
                return true;
        }

        return false;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void updateDisplayName(boolean updateBackends) {
        UUID uuid = player.getUniqueId();
        CachedMetaData data = lp.getUserManager().getUser(uuid).getCachedData().getMetaData();
        String prefix = data.getPrefix();
        String suffix = data.getSuffix();
        boolean hasPrefix = prefix != null;
        boolean hasSuffix = suffix != null;

        if (nickname == null)
            nickname = getName();

        if (!hasPrefix)
            prefix = "";

        if (!hasSuffix)
            suffix = "";

        displayName = ColorUtils.translate(hasPrefix ? prefix + " " : "") + nickname + (hasSuffix ? suffix + " " : "");

        if (updateBackends) {
            ByteArrayDataOutput output = ByteStreams.newDataOutput();
            output.writeUTF(uuid.toString());
            output.writeUTF(displayName);

            player.getServer().getInfo().sendData("dn:displayname", output.toByteArray());
        }
    }

    public String getReplyTarget() {
        return replyTarget;
    }

    public void setReplyTarget(String replyTarget) {
        this.replyTarget = replyTarget;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public boolean inLocalChat() {
        return localChat;
    }

    public void setLocalChat(boolean localChat) {
        this.localChat = localChat;
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

    public boolean inSignSpy() {
        return signSpy;
    }

    public void setSignSpy(boolean signSpy) {
        this.signSpy = signSpy;

        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF(player.getUniqueId().toString());
        output.writeBoolean(signSpy);

        player.getServer().getInfo().sendData("dn:signspy", output.toByteArray());
    }

    public boolean isVanished() {
        return vanished;
    }

    public void setVanished(boolean vanished) {
        this.vanished = vanished;

        String name = player.getName();
        String uuid = player.getUniqueId().toString();

        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF(uuid);
        output.writeBoolean(vanished);

        player.getServer().getInfo().sendData("dn:vanish", output.toByteArray());

        MessageBuilder staff = new MessageBuilder();
        MessageBuilder broadcast = new MessageBuilder();

        staff.append("&3&l» ");
        staff.append("&6" + displayName).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + name);

        if (vanished) {
            broadcast.append("&c&l» ");
            broadcast.append("&6" + displayName).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + name);
            broadcast.append("&c left the server.");

            staff.append("&3 has vanished. Poof");
        } else {
            broadcast.append("&a&l» ");
            broadcast.append("&6" + displayName).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + name);
            broadcast.append("&a joined the server.");

            staff.append("&3 is now visible.");
        }

        plugin.getProxy().getPluginManager().callEvent(new UserVanishEvent(this, vanished));

        MessageUtils.broadcast(PermissionType.NONE, broadcast.build());
        MessageUtils.broadcast(PermissionType.STAFF, staff.build());
    }

    public ProtocolVersion getVersion() {
        return ProtocolVersion.fromId(player.getPendingConnection().getVersion());
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public void sendMessage(String message) {
        sendMessage(TextComponent.fromLegacyText(message));
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
