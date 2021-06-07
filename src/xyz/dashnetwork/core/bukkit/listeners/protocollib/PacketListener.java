package xyz.dashnetwork.core.bukkit.listeners.protocollib;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.injector.GamePhase;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import xyz.dashnetwork.core.bukkit.Core;
import xyz.dashnetwork.core.bukkit.events.UserPacketEvent;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PacketListener extends PacketAdapter {

    private static Core plugin = Core.getInstance();
    private static Set<PacketType> enabledPackets = StreamSupport.stream(PacketType.values().spliterator(), false).filter(type -> type.isSupported()).collect(Collectors.toSet());

    public PacketListener() {
        super(new AdapterParameteters().gamePhase(GamePhase.BOTH).plugin(plugin).types(enabledPackets).listenerPriority(ListenerPriority.HIGHEST).options(ListenerOptions.ASYNC));
    }

    public void start() {
        ProtocolLibrary.getProtocolManager().addPacketListener(this);
    }

    public void stop() {
        ProtocolLibrary.getProtocolManager().removePacketListener(this);
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        UserPacketEvent userPacketEvent = new UserPacketEvent(event, UserPacketEvent.PacketEventType.RECEIVED);

        Bukkit.getPluginManager().callEvent(userPacketEvent);

        event.setCancelled(userPacketEvent.isCancelled());
        event.setPacket(userPacketEvent.getPacket());
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        UserPacketEvent userPacketEvent = new UserPacketEvent(event, UserPacketEvent.PacketEventType.SENT);

        Bukkit.getPluginManager().callEvent(userPacketEvent);

        event.setCancelled(userPacketEvent.isCancelled());
        event.setPacket(userPacketEvent.getPacket());
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public ListeningWhitelist getReceivingWhitelist() {
        return ListeningWhitelist.newBuilder().gamePhase(GamePhase.BOTH).types(enabledPackets).priority(ListenerPriority.HIGHEST).build();
    }

    @Override
    public ListeningWhitelist getSendingWhitelist() {
        return ListeningWhitelist.newBuilder().gamePhase(GamePhase.BOTH).types(enabledPackets).priority(ListenerPriority.HIGHEST).build();
    }

}
