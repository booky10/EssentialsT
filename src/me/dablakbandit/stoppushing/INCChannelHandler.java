// 
// Decompiled by Procyon v0.5.36
// 

package me.dablakbandit.stoppushing;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.lang.reflect.Field;

public class INCChannelHandler implements Listener
{
    private static Class<?> entityPlayer;
    private static Class<?> playerConnection;
    private static Class<?> networkManager;
    private static Field channelField;
    private static Field network;
    private static Field connection;
    
    static {
        INCChannelHandler.entityPlayer = NMSUtils.getNMSClass("EntityPlayer");
        INCChannelHandler.playerConnection = NMSUtils.getNMSClass("PlayerConnection");
        INCChannelHandler.networkManager = NMSUtils.getNMSClass("NetworkManager");
        INCChannelHandler.channelField = getChannelField();
        INCChannelHandler.network = NMSUtils.getField(INCChannelHandler.playerConnection, "networkManager");
        INCChannelHandler.connection = NMSUtils.getField(INCChannelHandler.entityPlayer, "playerConnection");
    }
    
    public INCChannelHandler() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            this.removeChannel(player);
            this.addChannel(player);
        }
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        this.addChannel(event.getPlayer());
    }
    
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        this.removeChannel(event.getPlayer());
    }
    
    @EventHandler
    public void onPlayerKick(final PlayerKickEvent event) {
        this.removeChannel(event.getPlayer());
    }
    
    private static Field getChannelField() {
        Field channelField = null;
        try {
            channelField = NMSUtils.getFirstFieldOfTypeWithException(INCChannelHandler.networkManager, Channel.class);
        }
        catch (Exception e) {
            System.out.print("Channel class not found");
        }
        if (channelField != null) {
            channelField.setAccessible(true);
        }
        return channelField;
    }
    
    public void addChannel(final Player player) {
        try {
            final Object handle = NMSUtils.getHandle(player);
            final Object connection = INCChannelHandler.connection.get(handle);
            final Channel channel = (Channel)INCChannelHandler.channelField.get(INCChannelHandler.network.get(connection));
            new Thread(() -> {
                try {
                    channel.pipeline().addBefore("packet_handler", "stop_pushing_handler", new PlayerChannelHandler());
                }
                catch (Exception ignored) {}
            }, "StopPushing Player Channel Adder").start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void removeChannel(final Player player) {
        try {
            final Object handle = NMSUtils.getHandle(player);
            final Object connection = INCChannelHandler.connection.get(handle);
            final Channel channel = (Channel)INCChannelHandler.channelField.get(INCChannelHandler.network.get(connection));
            new Thread(() -> {
                try {
                    channel.pipeline().remove("stop_pushing_handler");
                }
                catch (Exception ignored) {}
            }, "StopPushing Player Channel Remover").start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static class PlayerChannelHandler extends ChannelDuplexHandler
    {
        public PlayerChannelHandler() {
        }

        public void write(final ChannelHandlerContext ctx, final Object msg, final ChannelPromise promise) throws Exception {
            StopPushingPlugin.parse(msg);
            super.write(ctx, msg, promise);
        }
        
        public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
            super.channelRead(ctx, msg);
        }
    }
}
