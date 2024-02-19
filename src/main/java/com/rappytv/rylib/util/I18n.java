package com.rappytv.rylib.util;

import com.rappytv.rylib.RyLib;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class I18n {

    private final FileConfiguration config;

    public <T extends JavaPlugin> I18n(T plugin) {
        this.config = plugin.getConfig();
    }

    public static String prefix() {
        return RyLib.get().i18n().translate("prefix");
    }

    public String translate(String key, Argument... args) {
        String translation = config.getString("i18n." + key);
        if(translation == null) return key;
        if(translation.contains("<prefix>"))
            translation = translation.replace("<prefix>", prefix());
        for(Argument argument : args)
            translation = argument.apply(translation);

        return Colors.translateCodes(translation);
    }

    public record Argument(CharSequence argument, CharSequence replacement) {

        public Argument(CharSequence argument, Number number) {
            this(argument, String.valueOf(number));
        }

        public String apply(String text) {
            return text.replace("<" + argument + ">", replacement);
        }
    }
}
