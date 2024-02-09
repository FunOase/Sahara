package com.rappytv.rylib;

import com.rappytv.rylib.command.RyLibCommand;
import com.rappytv.rylib.util.I18n;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public final class RyLib extends JavaPlugin {

    private I18n i18n;
    private static RyLib instance;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        i18n = new I18n(this);
        instance = this;

        new RyLibCommand("rylib", this);
    }

    @Override
    public void onDisable() {}

    public I18n i18n() {
        return i18n;
    }

    public static RyLib get() {
        return instance;
    }
}
