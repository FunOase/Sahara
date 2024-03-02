package com.rappytv.rylib.util;

import com.rappytv.rylib.RyLib;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
public class I18n {

    private static final Set<I18n> instances = new HashSet<>();
    private final JavaPlugin plugin;

    public <T extends JavaPlugin> I18n(T plugin) {
        this.plugin = plugin;
        instances.add(this);
    }

    public static void reload() {
        for(I18n instance : instances) {
            instance.plugin.reloadConfig();
        }
    }

    public static String prefix() {
        return RyLib.get().i18n().translate("prefix");
    }

    public String translate(String key, Argument... args) {
        String translation = plugin.getConfig().getString("i18n." + key);
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
