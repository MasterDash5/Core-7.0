package dashnetwork.core.bukkit.utils;

import dashnetwork.core.utils.ColorUtils;
import dashnetwork.core.utils.LazyUtils;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class User implements CommandSender {

    private static List<User> users = new CopyOnWriteArrayList<>();
    private static LuckPerms lp = LuckPermsProvider.get();
    private List<UserAddon> addons;
    private Player player;
    private String displayName;
    private boolean vanished, bookSpy, signSpy;

    private User(Player player) {
        this.addons = new CopyOnWriteArrayList<>();
        this.player = player;
        this.displayName = defaultDisplayName();
        this.vanished = false;
        this.bookSpy = false;
        this.signSpy = false;

        users.add(this);
    }

    public static List<User> getUsers(boolean createNew) {
        if (createNew)
            for (Player online : Bukkit.getOnlinePlayers())
                getUser(online);
        return users;
    }

    public static User getUser(Player player) {
        for (User user : users)
            if (user.getPlayer().equals(player))
                return user;
        return new User(player);
    }

    private String defaultDisplayName() {
        CachedMetaData data = lp.getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData();
        String prefix = data.getPrefix();
        String suffix = data.getSuffix();
        boolean hasPrefix = prefix != null;
        boolean hasSuffix = suffix != null;

        if (!hasPrefix)
            prefix = "";

        if (!hasSuffix)
            suffix = "";

        return ColorUtils.translate(hasPrefix ? prefix + " " : "") + getName() + (hasSuffix ? suffix + " " : "");
    }

    public void remove() {
        // TODO: Save anything

        users.remove(this);
    }

    public void addAddon(UserAddon addon) {
        addons.add(addon);
    }

    public <T>void removeAddon(T clazz) {
        for (UserAddon addon : addons)
            if (addon.getClass().equals(clazz))
                addons.remove(addon);
    }

    public <T>UserAddon getAddon(T clazz) {
        for (UserAddon addon : addons)
            if (addon.getClass().equals(clazz))
                return addon;
        return null;
    }

    public <T>boolean hasAddon(T clazz) {
        for (UserAddon addon : addons)
            if (addon.getClass().equals(clazz))
                return true;
        return false;
    }

    public Player getPlayer() {
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isVanished() {
        return vanished;
    }

    public void setVanished(boolean vanished) {
        this.vanished = vanished;

        if (vanished) {
            for (User online : getUsers(true))
                if (!online.equals(this) && !online.isStaff())
                    online.getPlayer().hidePlayer(player);
        } else
            for (Player online : Bukkit.getOnlinePlayers())
                online.showPlayer(player);
    }

    public boolean inBookSpy() {
        return bookSpy;
    }

    public void setBookSpy(boolean bookSpy) {
        this.bookSpy = bookSpy;
    }

    public boolean inSignSpy() {
        return signSpy;
    }

    public void setSignSpy(boolean signSpy) {
        this.signSpy = signSpy;
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
    public void sendMessage(String... messages) {
        for (String message : messages)
            sendMessage(message);
    }

    @Override
    public void sendMessage(BaseComponent component) {
        player.sendMessage(component);
    }

    @Override
    public void sendMessage(BaseComponent... components) {
        player.sendMessage(components);
    }

    @Override
    public Server getServer() {
        return player.getServer();
    }

    @Override
    public boolean isPermissionSet(String permission) {
        return player.isPermissionSet(permission);
    }

    @Override
    public boolean isPermissionSet(Permission permission) {
        return player.isPermissionSet(permission);
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public boolean hasPermission(Permission permission) {
        return player.hasPermission(permission);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        return player.addAttachment(plugin);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, int number) {
        return player.addAttachment(plugin, number);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String string, boolean value) {
        return player.addAttachment(plugin, string, value);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String string, boolean value, int number) {
        return player.addAttachment(plugin, string, value, number);
    }

    @Override
    public void removeAttachment(PermissionAttachment attachment) {
        player.removeAttachment(attachment);
    }

    @Override
    public void recalculatePermissions() {
        player.recalculatePermissions();
    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return player.getEffectivePermissions();
    }

    @Override
    public boolean isOp() {
        return player.isOp() || isOwner();
    }

    @Override
    public void setOp(boolean op) {
        player.setOp(op);
    }

}
