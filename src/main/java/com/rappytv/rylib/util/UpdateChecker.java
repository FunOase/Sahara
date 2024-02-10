package com.rappytv.rylib.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("rawtypes")
public class UpdateChecker<T extends JavaPlugin> {

    private static final Set<UpdateChecker> checkers = new HashSet<>();

    public static Set<UpdateChecker> getCheckers() {
        return checkers;
    }

    private final T plugin;
    private final String version;
    private String latestVersion = null;
    private String artifactUrl = null;

    public UpdateChecker(T plugin) {
        this.plugin = plugin;
        this.version = plugin.getDescription().getVersion();
        checkers.add(this);
    }

    public String getPluginName() {
        return plugin.getName();
    }

    public String getVersion() {
        return version;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    public boolean isEnabled() {
        return plugin.getConfig().isBoolean("checkForUpdates")
                && plugin.getConfig().getBoolean("checkForUpdates");
    }

    public boolean isUpdateAvailable() {
        if(!isEnabled()) return false;
        if(artifactUrl == null || latestVersion == null) return false;
        try {
            int current = Integer.parseInt(version.replace(".", ""));
            int latest = Integer.parseInt(latestVersion.replace(".", ""));

            return latest > current;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    public UpdateChecker<T> setArtifactFormat(String domain, String pkg) {
        return setArtifactFormat(domain, getPluginName(), pkg);
    }

    public UpdateChecker<T> setArtifactFormat(String domain, String job, String pkg, String... folders) {
        StringBuilder folderString = new StringBuilder();
        for (String folder : folders) {
            folderString.append(folder.replace(" ", "%20")).append("/job");
        }

        artifactUrl = String.format(
                "https://%s/job/%s/%s/lastSuccessfulBuild/%s$%s/api/json",
                domain,
                folderString,
                job,
                pkg,
                getPluginName()
        );
        setLatestVersion();
        return this;
    }

    private void setLatestVersion() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(new URI(artifactUrl))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept((response) -> {
                JsonObject body = JsonParser.parseString(response.body()).getAsJsonObject();
                JsonObject artifact = body.getAsJsonArray("artifacts").get(0).getAsJsonObject();
                String path = artifact.get("displayPath").getAsString();
                this.latestVersion = path.substring(path.lastIndexOf("-") + 1, path.lastIndexOf(".jar"));
            });
        } catch (JsonParseException | URISyntaxException exception) {
            plugin.getLogger().severe("Invalid API response for " + getPluginName() + " update checker!");
            plugin.getLogger().severe(exception.getMessage());
        }
    }
}
