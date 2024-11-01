package net.funoase.sahara.common.i18n;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.*;

public class Language {

    private final static Gson gson = new Gson();
    private final I18nManager manager;
    private final String code;
    private final Set<File> translationFiles = new HashSet<>();
    private final Map<String, String> translations = new HashMap<>();

    public Language(I18nManager manager, String code) {
        this.manager = manager;
        this.code = code;
        loadTranslations();
    }

    public void addTranslationFile(File file) {
        this.translationFiles.add(file);
    }

    private void loadTranslations() {
        for (File file : translationFiles) {
            JsonElement element;
            try {
                element = gson.fromJson(new FileReader(Path.of(file.getAbsolutePath(), code + ".json").toString()), JsonElement.class);
            } catch (FileNotFoundException | JsonSyntaxException e) {
                this.manager.getLogger().throwing(getClass().getName(), "loadTranslations", e);
                return;
            }

            if(!element.isJsonObject()) {
                this.manager.getLogger().severe("Invalid language file: " + file.getPath());
                return;
            }

            JsonObject object = element.getAsJsonObject();
            for(Map.Entry<String, JsonElement> entry : object.entrySet()) {
                this.readJsonTree(entry.getKey(), entry.getValue());
            }
        }
    }

    private void readJsonTree(String key, JsonElement element) {
        if(!element.isJsonObject()) {
            if(element.isJsonPrimitive()) {
                translations.putIfAbsent(key, element.getAsString());
            }
        } else {
            JsonObject object = element.getAsJsonObject();

            for(Map.Entry<String, JsonElement> entry : object.entrySet()) {
                this.readJsonTree(key + "." + entry.getKey(), entry.getValue());
            }
        }
    }

    public void reloadTranslations() {
        this.translations.clear();
        this.loadTranslations();
    }

    @NotNull
    public String getCode() {
        return code;
    }

    @NotNull
    public String getRawTranslation(@NotNull String key) {
        String translation = this.translations.get(key);
        if(translation == null) translation = manager.getFallbackLanguage().getTranslation(key);
        return translation != null ? translation : key;
    }

    @NotNull
    public String translate(@NotNull String key, Object... args) {
        try {
            return String.format(this.getRawTranslation(key), args);
        } catch (IllegalFormatException e) {
            return key;
        }
    }

    @Nullable
    public String getTranslation(@NotNull String key, Object... args) {
        String translate = this.translate(key, args);
        return translate.equals(key) ? null : translate;
    }

    public boolean has(@NotNull String key) {
        return this.translations.containsKey(key) || manager.getFallbackLanguage().getTranslation(key) != null;
    }
}
