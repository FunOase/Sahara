package com.rappytv.rylib.listeners;

import com.rappytv.rylib.RyLib;
import com.rappytv.rylib.util.I18n;
import com.rappytv.rylib.util.UpdateChecker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class PlayerListener implements Listener {

    private final RyLib plugin;

    public PlayerListener(RyLib plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(!player.hasPermission("rylib.updates")) return;
        List<UpdateChecker> availableCheckers = new ArrayList<>();

        for(UpdateChecker checker : UpdateChecker.getCheckers()) {
            if(checker.isEnabled() && checker.isUpdateAvailable())
                availableCheckers.add(checker);
        }
        String plugins = String.join(
                "\n",
                availableCheckers
                        .stream()
                        .map((pl) ->
                                plugin.i18n().translate(
                                        "private.updatedPlugin",
                                        new I18n.Argument("name", pl.getPluginName()),
                                        new I18n.Argument("current", pl.getVersion()),
                                        new I18n.Argument("latest", pl.getLatestVersion())
                                )
                        ).toList()
        );

        if(!availableCheckers.isEmpty()) {
            player.sendMessage(
                    plugin.i18n().translate(
                            "private.update",
                            new I18n.Argument("plugins", plugins)
                    )
            );
        }
    }
}
