package net.funoase.sahara.common.i18n;

import net.funoase.sahara.bukkit.Sahara;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;
import java.util.logging.Logger;

public class I18nManager {

    public static final Locale fallback = Locale.of("en_US");
    private final Logger logger;

    private final Set<File> sourceDirectories = new HashSet<>();
    private final Map<Locale, Language> languages = new HashMap<>();

    public I18nManager(Logger logger) {
        this.logger = logger;
    }

    public void saveTranslations(JavaPlugin plugin) {
        final List<String> languages = Sahara.get().getConfig().getStringList("languages");
        final File directory = new File(plugin.getDataFolder(), "i18n");
        for (final String language : languages) {
            if (!new File(directory, String.format("%s.json", language)).exists()) {
                if(plugin.getResource(String.format("i18n/%s.json", language)) == null) continue;
                plugin.saveResource(String.format("i18n/%s.json", language), false);
            }
        }
        addSource(directory);
    }

    public void addSource(File directory) {
        if(!directory.exists() || !directory.isDirectory()) throw new IllegalArgumentException("Invalid directory: " + directory);
        sourceDirectories.add(directory);
        loadLanguages();
    }

    public void loadLanguages() {
        for (File directory : sourceDirectories) {
            File[] files = directory.listFiles();
            if (files == null) continue;
            for (File file : files) {
                if (!file.getName().endsWith(".json")) continue;
                Locale locale = Locale.of(file.getName().replace(".json", ""));
                Language language = languages.get(locale);
                if(language == null) {
                    language = new Language(this, locale);
                    languages.put(locale, language);
                }
                language.addTranslationFile(file);
                language.reloadTranslations();
            }
        }
        logger.info(languages.size() + " languages are available.");
    }

    public Logger getLogger() {
        return logger;
    }

    public Language getFallbackLanguage() {
        return languages.get(fallback);
    }

    public Language getLanguage(String code) {
        return languages.getOrDefault(Locale.of(code.toLowerCase()), getFallbackLanguage());
    }

    public Language getLanguage(Locale locale) {
        return getLanguage(locale.toString().toLowerCase());
    }
}
