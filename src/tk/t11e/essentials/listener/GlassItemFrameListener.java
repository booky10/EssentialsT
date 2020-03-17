package tk.t11e.essentials.listener;
// Created by booky10 in EssentialsT (19:40 05.03.20)

import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import tk.t11e.api.packets.PacketUseEntityEvent;
import tk.t11e.essentials.main.Main;

public class GlassItemFrameListener implements Listener {

    @EventHandler
    public void onDamage(PacketUseEntityEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.main, () -> {
            if (!event.getAction().equals(EnumWrappers.EntityUseAction.ATTACK)) return;
            Entity entity = event.getEntity();
            Bukkit.broadcastMessage("3");
            if (!(entity instanceof ItemFrame)) return;
            ItemFrame frame = (ItemFrame) entity;
            Bukkit.broadcastMessage("1");
            if (!entity.getCustomName().contains("Glass Item Frame")) return;
            Bukkit.broadcastMessage("2");
            //TO-DO
        });
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.main, () -> {
            if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
            Player player = event.getPlayer();
            boolean right;
            String localizedName;
            ItemStack mainHand = null, offHand = null;
            if (player.getInventory().getItemInMainHand().hasItemMeta()) {
                mainHand = player.getInventory().getItemInMainHand();
                localizedName = mainHand.getItemMeta().getLocalizedName();
                right = true;
            } else if (player.getInventory().getItemInOffHand().hasItemMeta()) {
                offHand = player.getInventory().getItemInOffHand();
                localizedName = offHand.getItemMeta().getLocalizedName();
                right = false;
            } else
                return;
            if (!localizedName.contains("Glass Item Frame")) return;
            event.setCancelled(true);
            if (!player.getGameMode().equals(GameMode.CREATIVE)) {
                if (right) {
                    mainHand.setAmount(mainHand.getAmount() - 1);
                    player.getInventory().setItemInMainHand(mainHand);
                } else {
                    offHand.setAmount(offHand.getAmount() - 1);
                    player.getInventory().setItemInOffHand(offHand);
                }
            }
            Block targetBlock = player.getTargetBlock(10);
            if (!targetBlock.getType().isSolid()) return;
            try {
                Location summonLocation = targetBlock.getLocation().add(event.getBlockFace().getDirection());
                ItemFrame frame = summonLocation.getWorld().spawn(summonLocation, ItemFrame.class);
                frame.setCustomName(localizedName);
                frame.getWorld().playSound(summonLocation, Sound.ENTITY_ITEM_FRAME_PLACE, 1, 1);
            } catch (IllegalArgumentException ignored) {
            }
        });
    }
}