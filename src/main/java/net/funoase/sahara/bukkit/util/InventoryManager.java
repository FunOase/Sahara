package net.funoase.sahara.bukkit.util;

import net.funoase.sahara.bukkit.Sahara;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class InventoryManager {

    private final NamespacedKey itemInteractionAction;
    private final Map<String, InventoryHolder> inventoryHolders = new HashMap<>();

    public InventoryManager(Sahara plugin) {
        this.itemInteractionAction = new NamespacedKey(plugin, "item_interaction_action");
    }

    public void registerInventory(String value, ItemInteractionType type, Function<Player, Inventory> inventory) {
        inventoryHolders.putIfAbsent(value, new InventoryHolder(value, type, inventory));
    }

    public ItemStack putValueOnItem(ItemStack itemStack, String value) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(this.itemInteractionAction, PersistentDataType.STRING, value);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public boolean onInventoryInteract(Player player, ItemStack itemStack, ItemInteractionType type) {
        if(!itemStack.getPersistentDataContainer().has(this.itemInteractionAction)) return false;
        String value = itemStack.getPersistentDataContainer().get(this.itemInteractionAction, PersistentDataType.STRING);
        InventoryHolder holder = inventoryHolders.get(value);
        if(holder.type != type && holder.type != ItemInteractionType.BOTH) return false;
        if(!holder.value.equals(value)) return false;
        player.openInventory(holder.inventoryFunction.apply(player));
        return true;
    }

    public record InventoryHolder(@NotNull String value,
                                  @NotNull ItemInteractionType type,
                                  @NotNull Function<Player, Inventory> inventoryFunction) {}

    public enum ItemInteractionType {
        INVENTORY_CLICK,
        HOTBAR_INTERACT,
        BOTH
    }
}
