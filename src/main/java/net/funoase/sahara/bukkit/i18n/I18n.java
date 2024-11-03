package net.funoase.sahara.bukkit.i18n;

import net.funoase.sahara.bukkit.Sahara;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class I18n {

    @NotNull
    public static String getRawTranslation(@NotNull Player player, @NotNull String key) {
        return Sahara.get().getI18nManager().getLanguage(player.locale()).getRawTranslation(key);
    }

    @NotNull
    public static String translate(@NotNull Player player, @NotNull String key, Object... args) {
        return Sahara.get().getI18nManager().getLanguage(player.locale()).translate(key, args);
    }

    @Nullable
    public static String getTranslation(@NotNull Player player, @NotNull String key, Object... args) {
        return Sahara.get().getI18nManager().getLanguage(player.locale()).getTranslation(key, args);
    }

    public static boolean has(@NotNull Player player, @NotNull String key) {
        return Sahara.get().getI18nManager().getLanguage(player.locale()).has(key);
    }
}
