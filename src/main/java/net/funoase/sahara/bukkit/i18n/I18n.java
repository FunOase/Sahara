package net.funoase.sahara.bukkit.i18n;

import net.funoase.sahara.bukkit.Sahara;
import net.funoase.sahara.common.i18n.I18nManager;
import net.funoase.sahara.common.i18n.Language;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class I18n {

    private static final I18nManager manager = Sahara.get().getI18nManager();
    private static final MiniMessage minimessage = MiniMessage.miniMessage();

    public static Component component(CommandSender sender, String key, TagResolver... args) {
        return component(sender, key, false, args);
    }

    public static Component component(Language language, String key, TagResolver... args) {
        return component(language, key, false, args);
    }

    public static Component component(CommandSender sender, String key, boolean prefix, TagResolver... args) {
        return component(manager.getLanguage(sender), key, prefix, args);
    }

    public static Component component(Language language, String key, boolean prefix, TagResolver... args) {
        return minimessage.deserialize((prefix ? language.translate("sahara.prefix") + " " : "") + language.translate(key), args);
    }

    @NotNull
    public static String getRawTranslation(@NotNull CommandSender sender, @NotNull String key) {
        return manager.getLanguage(sender).getRawTranslation(key);
    }

    @NotNull
    public static String translate(@NotNull CommandSender sender, @NotNull String key, Object... args) {
        return manager.getLanguage(sender).translate(key, args);
    }

    @Nullable
    public static String getTranslation(@NotNull CommandSender sender, @NotNull String key, Object... args) {
        return manager.getLanguage(sender).getTranslation(key, args);
    }

    public static boolean has(@NotNull CommandSender sender, @NotNull String key) {
        return manager.getLanguage(sender).has(key);
    }
}
