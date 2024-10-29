package net.funoase.sahara.command;

import net.funoase.sahara.Sahara;
import net.funoase.sahara.util.Command;
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
        sender.sendMessage("sahara.commands.reload.success");
    }

    @Override
    public List<String> complete(CommandSender sender, String prefix, String[] args) {
        return null;
    }
}
