package net.funoase.sahara.common.i18n;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class I18nManager {

    public static final String fallbackCode = "en_us";
    private final Logger logger;

    private final Set<File> sourceDirectories = new HashSet<>();
    private final Map<String, Language> languages = new HashMap<>();

    public I18nManager(Logger logger) {
        this.logger = logger;
        loadLanguages();
    }

    public void loadTranslations(File directory) {
        if(!directory.exists() || !directory.isDirectory()) throw new IllegalArgumentException("Invalid directory: " + directory);
        sourceDirectories.add(directory);
    }

    public void loadLanguages() {
        for (File directory : sourceDirectories) {
            File[] files = directory.listFiles();
            if (files == null) continue;
            for (File file : files) {
                if (!file.getName().endsWith(".json")) continue;
                String code = file.getName().replace(".json", "");
                Language language = languages.get(code);
                if(language == null) {
                    language = new Language(this, code);
                    languages.put(language.getCode(), language);
                }
                language.addTranslationFile(file);
            }
        }
        logger.info("Loaded " + languages.size() + " languages.");
    }

    public Language getLanguage(String code) {
        return languages.getOrDefault(code, getFallbackLanguage());
    }

    public Language getFallbackLanguage() {
        return languages.get(fallbackCode);
    }

    public Logger getLogger() {
        return logger;
    }
}
