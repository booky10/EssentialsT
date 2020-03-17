package tk.t11e.essentials.listener;
// Created by booky10 in EssentialsT (15:01 14.02.20)

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import tk.t11e.essentials.main.Main;
import tk.t11e.essentials.util.ItemSorter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortingListener implements Listener {

    @EventHandler
    public void onSorting(InventoryClickEvent event) {
        if (!event.getWhoClicked().getType().equals(EntityType.PLAYER)) return;
        if (!event.getClick().equals(ClickType.MIDDLE)) return;
        if (!event.getSlotType().equals(InventoryType.SlotType.CONTAINER)) return;
        if (!event.getAction().equals(InventoryAction.NOTHING)) return;

        Player player = (Player) event.getWhoClicked();

        if (player.getGameMode().equals(GameMode.CREATIVE)
                || player.getGameMode().equals(GameMode.SURVIVAL)) {
            event.setCancelled(true);

            if (event.getRawSlot() >= event.getInventory().getSize())
                ItemSorter.sort(event.getView().getBottomInventory());
            else
                ItemSorter.sort(event.getInventory());

            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK,1,1);
        }
    }
}