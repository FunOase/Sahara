package net.funoase.sahara.bukkit.listeners;

import net.funoase.sahara.bukkit.Sahara;
import net.funoase.sahara.bukkit.util.InventoryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemInteractionListener implements Listener {

    private final Sahara plugin;

    public ItemInteractionListener(Sahara plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();
        if(currentItem == null) return;
        plugin.getInventoryManager().onInventoryInteract(player, currentItem, InventoryManager.ItemInteractionType.INVENTORY_CLICK);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack currentItem = event.getItem();
        if(currentItem == null) return;
        if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        event.setCancelled(plugin.getInventoryManager().onInventoryInteract(player, currentItem, InventoryManager.ItemInteractionType.HOTBAR_INTERACT));
    }
}
