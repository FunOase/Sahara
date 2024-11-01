package net.funoase.sahara.bukkit.i18n;

import net.funoase.sahara.common.i18n.Internationalization;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class I18n implements Internationalization<Player> {

    @Override
    public @NotNull String getRawTranslation(@NotNull Player player, @NotNull String key) {
        return "";
    }

    @Override
    public @NotNull String translate(@NotNull Player player, @NotNull String key, Object... args) {
        return "";
    }

    @Override
    public @Nullable String getTranslation(@NotNull Player player, @NotNull String key, Object... args) {
        return "";
    }

    @Override
    public boolean has(@NotNull Player player, @NotNull String key) {
        return false;
    }
}
