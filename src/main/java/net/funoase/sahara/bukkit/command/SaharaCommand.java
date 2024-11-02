package net.funoase.sahara.bukkit.command;

import net.funoase.sahara.bukkit.Sahara;
import net.funoase.sahara.bukkit.util.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class SaharaCommand extends Command<Sahara> {

    public SaharaCommand(String name, Sahara plugin) {
        super(name, plugin);
    }

    @Override
    public void execute(CommandSender sender, String prefix, String[] args) {
        if(!sender.hasPermission("sahara.reload")) {
            sender.sendMessage("sahara.errors.noPermission");
            return;
        }
        plugin.reloadConfig();
        plugin.getI18nManager().loadLanguages();
        sender.sendMessage("sahara.commands.reload.success");
    }

    @Override
    public List<String> complete(CommandSender sender, String prefix, String[] args) {
        return null;
    }
}
