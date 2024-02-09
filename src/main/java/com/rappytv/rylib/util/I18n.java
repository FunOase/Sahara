package com.rappytv.rylib.util;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class I18n {

    private final FileConfiguration config;

    public <T extends JavaPlugin> I18n(T plugin) {
        this.config = plugin.getConfig();
    }

    public String prefix() {
        return translate("msg.prefix");
    }

    public String translate(String key) {
        return translate(key, true);
    }

    public String translate(String key, boolean color) {
        String translation = config.getString(key);
        if(translation == null) return key;
        if(translation.contains("<prefix>"))
            translation = translation.replace("<prefix>", prefix());
        if(color) return ChatColor.translateAlternateColorCodes('&', translation);
        return translation;
    }
}
