package tk.t11e.essentials.commands;
// Created by booky10 in EssentialsT (20:36 26.12.19)

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.t11e.essentials.main.Main;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("NullableProblems")
public class EssentialsT implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("essentialsT"))
                if (args.length == 1)
                    if (args[0].equalsIgnoreCase("reload")) {
                        Bukkit.getPluginManager().disablePlugin(Main.getPlugin(Main.class), true);
                        Bukkit.getPluginManager().enablePlugin(Main.getPlugin(Main.class));
                        player.sendMessage(Main.PREFIX + "§aSuccessfully reloaded plugin!");
                    } else if (args[0].equalsIgnoreCase("disable")) {
                        if (Bukkit.getPluginManager().isPluginEnabled(Main.getPlugin(Main.class))) {
                            Bukkit.getPluginManager().disablePlugin(Main.getPlugin(Main.class), true);
                            player.sendMessage(Main.PREFIX + "§aSuccessfully disabled plugin!");
                        } else
                            player.sendMessage(Main.PREFIX + "The plugin is already disabled!");
                    } else
                        return false;
                else
                    player.sendMessage(Main.NO_PERMISSION);
        } else if (args.length == 1)
            if (args[0].equalsIgnoreCase("reload")) {
                Bukkit.getPluginManager().disablePlugin(Main.getPlugin(Main.class));
                Bukkit.getPluginManager().enablePlugin(Main.getPlugin(Main.class));
                sender.sendMessage("Successfully reloaded plugin!");
            } else if (args[0].equalsIgnoreCase("disable")) {
                if (Bukkit.getPluginManager().isPluginEnabled(Main.getPlugin(Main.class))) {
                    Bukkit.getPluginManager().disablePlugin(Main.getPlugin(Main.class));
                    sender.sendMessage("Successfully disabled plugin!");
                } else
                    sender.sendMessage("The plugin is already disabled!");
            } else
                return false;
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            list.add("reload");
            list.add("disable");
        }
        return list;
    }
}