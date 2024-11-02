package net.funoase.sahara.bukkit;

import net.funoase.sahara.bukkit.command.SaharaCommand;
import net.funoase.sahara.common.database.Database;
import net.funoase.sahara.common.i18n.I18nManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public final class Sahara extends JavaPlugin {

    private static Sahara instance;
    private I18nManager i18nManager;
    private Database database;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadDatabase();
        i18nManager = new I18nManager(getLogger());
        database = new Database(getLogger());
        instance = this;

        new SaharaCommand("sahara", this).register();
    }

    private void loadDatabase() {
        FileConfiguration config = getConfig();
        this.database.connect(
                config.getString("database.host"),
                config.getInt("database.port"),
                config.getString("database.database"),
                config.getString("database.username"),
                config.getString("database.password")
        );
    }

    public I18nManager getI18nManager() {
        return i18nManager;
    }

    public static Sahara get() {
        return instance;
    }
}
