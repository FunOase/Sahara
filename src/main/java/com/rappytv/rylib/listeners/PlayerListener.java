package com.rappytv.rylib.listeners;

import com.rappytv.rylib.RyLib;
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
                                plugin.i18n().translate("private.updatedPlugin")
                                        .replace("<name>", pl.getPluginName())
                                        .replace("<current>", pl.getVersion())
                                        .replace("<latest>", pl.getLatestVersion())
                        ).toList()
        );

        if(!availableCheckers.isEmpty()) {
            player.sendMessage(
                    plugin.i18n()
                            .translate("private.update")
                            .replace(
                                    "<plugins>",
                                    plugins
                            )
            );
        }
    }
}
