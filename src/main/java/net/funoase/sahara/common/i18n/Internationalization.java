package net.funoase.sahara.common.i18n;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface Internationalization<P> {

    String getLanguage(UUID player);

    void setLanguage(UUID player, String code);

    /**
     * Gets the raw translation. Returns the key if no translation is found.
     *
     * @param key the translation key
     * @return the raw translation or translation key if no translation is found
     */
    @NotNull String getRawTranslation(@NotNull P player, @NotNull String key);

    /**
     * Gets the translation with the arguments replaced. Returns the key if no translation is found.
     *
     * @param key  the translation key
     * @param args the arguments for the translation
     * @return the translation key or translation key if no translation is found
     */
    @NotNull String translate(@NotNull P player, @NotNull String key, Object... args);

    /**
     * Gets the translation with the arguments replaced. Returns null if no translation is found.
     *
     * @param key  the translation key
     * @param args the arguments for the translation
     * @return the translation or null, if no translation is found
     */
    @Nullable String getTranslation(@NotNull P player, @NotNull String key, Object... args);

    /**
     * Returns whether the translation was found or not.
     *
     * @param key the translation key
     * @return if the translation is found
     */
    boolean has(@NotNull P player, @NotNull String key);

    default boolean isAssumedTranslatable(@NotNull String key) {
        return !key.contains(" ") && key.contains(".");
    }
}
