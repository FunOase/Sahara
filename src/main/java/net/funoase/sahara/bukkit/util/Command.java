package net.funoase.sahara.bukkit.util;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"ConstantConditions", "unused"})
public abstract class Command<T extends JavaPlugin> implements CommandExecutor, TabExecutor {

    public static final List<String> players = new ArrayList<>(Collections.singletonList("--players--"));
    protected static final MiniMessage minimessage = MiniMessage.miniMessage();
    protected final T plugin;
    private final String name;
    private boolean registered = false;

    public Command(String name, T plugin) {
        this.name = name;
        this.plugin = plugin;
    }

    public abstract void execute(CommandSender sender, String prefix, String[] args);
    public abstract List<String> complete(CommandSender sender, String prefix, String[] args);

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        execute(commandSender, s, strings);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        List<String> result = complete(commandSender, s, strings);
        if(result == null) return Collections.emptyList();
        if(result.equals(players)) return null;
        return result;
    }

    public List<String> tab(String str, List<String> list) {
        List<String> finalList = new ArrayList<>();

        for(String tab : list) {
            if(tab.toLowerCase().startsWith(str.toLowerCase()))
                finalList.add(tab);
        }

        return finalList;
    }

    public void register() {
        if(this.registered) {
            throw new IllegalStateException("Command already registered");
        }
        plugin.getCommand(this.name).setExecutor(this);
        this.registered = true;
    }
}