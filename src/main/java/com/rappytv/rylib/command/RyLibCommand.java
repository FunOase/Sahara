package com.rappytv.rylib.command;

import com.rappytv.rylib.RyLib;
import com.rappytv.rylib.util.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class RyLibCommand extends Command<RyLib> {

    public RyLibCommand(String name, RyLib plugin) {
        super(name, plugin);
    }

    @Override
    public void execute(CommandSender sender, String prefix, String[] args) {
        if(!sender.hasPermission("rylib.reload")) {
            sender.sendMessage(plugin.i18n().translate("noPermission"));
            return;
        }
        plugin.reloadConfig();
        sender.sendMessage(plugin.i18n().translate("private.reload"));
    }

    @Override
    public List<String> complete(CommandSender sender, String prefix, String[] args) {
        return null;
    }
}
