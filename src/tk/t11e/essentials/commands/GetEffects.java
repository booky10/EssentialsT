package tk.t11e.essentials.commands;
// Created by booky10 in EssentialsT (13:23 01.03.20)

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import tk.t11e.essentials.main.Main;

import java.util.ArrayList;
import java.util.List;

public class GetEffects implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("effect.get")) {
                if (args.length == 0)
                    if (player.getActivePotionEffects().size() == 0)
                        player.sendMessage(Main.PREFIX + "You don't have any active potion effects!");
                    else {
                        player.sendMessage("§e--------§6[Effects]§e---------");
                        for (PotionEffect effect : player.getActivePotionEffects())
                            player.sendMessage("§e  - " + effect.getType().getName());
                    }
                else if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null)
                        if (target.getActivePotionEffects().size() == 0)
                            player.sendMessage(Main.PREFIX + "The player don't have any active potion effects!");
                        else {
                            player.sendMessage("§e--------§6[Effects]§e---------");
                            for (PotionEffect effect : target.getActivePotionEffects())
                                player.sendMessage("§e  - " + effect.getType().getName());
                        }
                    else
                        player.sendMessage(Main.PREFIX + "Unknown player!");
                } else
                    return false;
            } else
                player.sendMessage(Main.NO_PERMISSION);
        } else if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null)
                if (target.getActivePotionEffects().size() == 0)
                    sender.sendMessage("The player don't have any active potion effects!");
                else {
                    sender.sendMessage("--------[Effects]---------");
                    for (PotionEffect effect : target.getActivePotionEffects())
                        sender.sendMessage("  - " + effect.getType().getName());
                }
            else
                sender.sendMessage("Unknown player!");
        } else
            return false;
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1)
            for (Player player : Bukkit.getOnlinePlayers())
                list.add(player.getName());
        return list;
    }
}