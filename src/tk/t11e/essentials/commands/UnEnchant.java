package tk.t11e.essentials.commands;
// Created by booky10 in EssentialsT (15:36 11.01.20)

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import tk.t11e.essentials.main.Main;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("NullableProblems")
public class UnEnchant implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("unEnchant")) {
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null)
                        if (!target.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                            for (Enchantment enchantment :
                                    target.getInventory().getItemInMainHand().getEnchantments().keySet())
                                target.getInventory().getItemInMainHand().removeEnchantment(enchantment);
                            player.sendMessage(Main.PREFIX + "§aSuccessfully un-enchanted the item!");
                        } else
                            player.sendMessage(Main.PREFIX + "You can not unEnchant a hand!");
                    else
                        player.sendMessage(Main.PREFIX + "Player not found!");
                } else
                    return false;
            } else
                player.sendMessage(Main.NO_PERMISSION);
        } else if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null)
                if (!target.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                    for (Enchantment enchantment :
                            target.getInventory().getItemInMainHand().getEnchantments().keySet())
                        target.getInventory().getItemInMainHand().removeEnchantment(enchantment);
                    sender.sendMessage("Successfully un-enchanted the item!");
                } else
                    sender.sendMessage("You can not unEnchant a hand!");
            else
                sender.sendMessage("Player not found!");
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