package tk.t11e.essentials.commands;
// Created by booky10 in EssentialsT (21:42 26.12.19)

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
public class GameMode implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("gameMode")) {
                if (args.length == 1)
                    if (args[0].equalsIgnoreCase("2")
                            || args[0].equalsIgnoreCase("a")
                            || args[0].equalsIgnoreCase("adventure"))
                        player.setGameMode(org.bukkit.GameMode.ADVENTURE);
                    else if (args[0].equalsIgnoreCase("1")
                            || args[0].equalsIgnoreCase("c")
                            || args[0].equalsIgnoreCase("creative"))
                        player.setGameMode(org.bukkit.GameMode.CREATIVE);
                    else if (args[0].equalsIgnoreCase("3")
                            || args[0].equalsIgnoreCase("sp")
                            || args[0].equalsIgnoreCase("spectator"))
                        player.setGameMode(org.bukkit.GameMode.SPECTATOR);
                    else if (args[0].equalsIgnoreCase("0")
                            || args[0].equalsIgnoreCase("s")
                            || args[0].equalsIgnoreCase("survival"))
                        player.setGameMode(org.bukkit.GameMode.SURVIVAL);
                    else
                        return false;
                else if (args.length == 2) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target != null)
                        if (args[0].equalsIgnoreCase("2")
                                || args[0].equalsIgnoreCase("a")
                                || args[0].equalsIgnoreCase("adventure"))
                            target.setGameMode(org.bukkit.GameMode.ADVENTURE);
                        else if (args[0].equalsIgnoreCase("1")
                                || args[0].equalsIgnoreCase("c")
                                || args[0].equalsIgnoreCase("creative"))
                            target.setGameMode(org.bukkit.GameMode.CREATIVE);
                        else if (args[0].equalsIgnoreCase("3")
                                || args[0].equalsIgnoreCase("sp")
                                || args[0].equalsIgnoreCase("spectator"))
                            target.setGameMode(org.bukkit.GameMode.SPECTATOR);
                        else if (args[0].equalsIgnoreCase("0")
                                || args[0].equalsIgnoreCase("s")
                                || args[0].equalsIgnoreCase("survival"))
                            target.setGameMode(org.bukkit.GameMode.SURVIVAL);
                        else
                            return false;
                    else
                        player.sendMessage(Main.PREFIX + "Player not found!");
                } else
                    return false;
            } else
                player.sendMessage(Main.NO_PERMISSION);
        } else if (args.length == 2) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target != null)
                if (args[0].equalsIgnoreCase("2")
                        || args[0].equalsIgnoreCase("a")
                        || args[0].equalsIgnoreCase("adventure"))
                    target.setGameMode(org.bukkit.GameMode.ADVENTURE);
                else if (args[0].equalsIgnoreCase("1")
                        || args[0].equalsIgnoreCase("c")
                        || args[0].equalsIgnoreCase("creative"))
                    target.setGameMode(org.bukkit.GameMode.CREATIVE);
                else if (args[0].equalsIgnoreCase("3")
                        || args[0].equalsIgnoreCase("sp")
                        || args[0].equalsIgnoreCase("spectator"))
                    target.setGameMode(org.bukkit.GameMode.SPECTATOR);
                else if (args[0].equalsIgnoreCase("0")
                        || args[0].equalsIgnoreCase("s")
                        || args[0].equalsIgnoreCase("survival"))
                    target.setGameMode(org.bukkit.GameMode.SURVIVAL);
                else
                    return false;
            else
                sender.sendMessage("Player not found!");
        } else
            return false;
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            list.add("adventure");
            list.add("creative");
            list.add("spectator");
            list.add("survival");
        }
        return list;
    }
}