package net.funoase.sahara.bukkit;

import net.funoase.sahara.bukkit.command.SaharaCommand;
import net.funoase.sahara.bukkit.listeners.ItemInteractionListener;
import net.funoase.sahara.bukkit.util.InventoryManager;
import net.funoase.sahara.common.i18n.I18nManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public final class Sahara extends JavaPlugin {

    private static final Logger log = LoggerFactory.getLogger(Sahara.class);
    private static Sahara instance;
    private I18nManager i18nManager;
    private InventoryManager inventoryManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        i18nManager = new I18nManager(getLogger());
        i18nManager.saveTranslations(this);
        inventoryManager = new InventoryManager(this);

        // Register commands
        new SaharaCommand("sahara", this).register();
        Bukkit.getPluginManager().registerEvents(new ItemInteractionListener(this), this);
    }

    public I18nManager getI18nManager() {
        return i18nManager;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public static Sahara get() {
        return instance;
    }
}
