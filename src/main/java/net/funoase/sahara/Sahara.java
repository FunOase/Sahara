package net.funoase.sahara;

import net.funoase.sahara.command.SaharaCommand;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public final class Sahara extends JavaPlugin {

    private static Sahara instance;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;

        new SaharaCommand("sahara", this).register();
    }

    public static Sahara get() {
        return instance;
    }
}
