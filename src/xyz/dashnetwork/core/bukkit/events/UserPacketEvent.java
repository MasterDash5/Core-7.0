package xyz.dashnetwork.core.bukkit.events;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import xyz.dashnetwork.core.bukkit.utils.User;

public class UserPacketEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private User user;
    private Player player;
    private PacketContainer packet;
    private PacketEventType type;
    private PacketEvent packetEvent;
    private boolean cancelled;

    public UserPacketEvent(PacketEvent event, PacketEventType type) {
        super(event.isAsync());

        Player player = event.getPlayer();

        if (!event.isPlayerTemporary() && player != null)
            this.user = User.getUser(player);
        else
            this.user = null;

        this.player = player;
        this.type = type;
        this.packet = event.getPacket();
        this.packetEvent = event;
        this.cancelled = event.isCancelled();
    }

    public User getUser() {
        return user;
    }

    public Player getPlayer() {
        return player;
    }

    public PacketContainer getPacket() {
        return packet;
    }

    public PacketEvent getPacketEvent() {
        return packetEvent;
    }

    public void setPacket(PacketContainer packet) {
        this.packet = packet;
    }

    public PacketEventType getType() {
        return type;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public enum PacketEventType { RECEIVED, SENT }

}
