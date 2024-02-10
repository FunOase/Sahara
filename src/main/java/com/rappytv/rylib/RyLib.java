package com.rappytv.rylib;

import com.rappytv.rylib.command.RyLibCommand;
import com.rappytv.rylib.listeners.PlayerListener;
import com.rappytv.rylib.util.I18n;
import com.rappytv.rylib.util.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public final class RyLib extends JavaPlugin {

    private I18n i18n;
    private static RyLib instance;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        i18n = new I18n(this);
        new UpdateChecker<>(this).setArtifactFormat(
                        "ci.rappytv.com",
                        "RY-Lib",
                        "com.rappytv",
                        "Minecraft Plugins"
        );
        instance = this;

        new RyLibCommand("rylib", this);

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerListener(this), this);
    }

    public I18n i18n() {
        return i18n;
    }

    public static RyLib get() {
        return instance;
    }
}
