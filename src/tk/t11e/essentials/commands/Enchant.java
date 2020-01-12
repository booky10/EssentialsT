package tk.t11e.essentials.commands;
// Created by booky10 in EssentialsT (15:36 11.01.20)

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tk.t11e.essentials.main.Main;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("NullableProblems")
public class Enchant implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("enchant")) {
                if (args.length == 3) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null)
                        if (!target.getInventory().getItemInMainHand().getType().equals(Material.AIR)
                                || args[1].equalsIgnoreCase("inv"))
                            if (!(args[1].equalsIgnoreCase("all")
                                    || args[1].equalsIgnoreCase("inv"))) {
                                try {

                                    NamespacedKey enchantmentKey = NamespacedKey.minecraft(args[1]);
                                    Enchantment enchantment = Enchantment.getByKey(enchantmentKey);

                                    int level = 32767;
                                    if (!(args[2].equalsIgnoreCase("max")
                                            || args[2].equalsIgnoreCase("nothing")))
                                        level = Integer.parseInt(args[2]);

                                    if (enchantment != null) {
                                        if (!args[2].equalsIgnoreCase("nothing")) {
                                            target.getInventory().getItemInMainHand()
                                                    .addUnsafeEnchantment(enchantment, level);
                                            player.sendMessage(Main.PREFIX + "§aSuccessfully enchanted" +
                                                    " item!");
                                        } else if (target.getInventory().getItemInMainHand()
                                                .containsEnchantment(enchantment)) {
                                            target.getInventory().getItemInMainHand()
                                                    .removeEnchantment(enchantment);
                                            player.sendMessage(Main.PREFIX + "§aSuccessfully removed " +
                                                    "enchantment!");
                                        } else
                                            player.sendMessage(Main.PREFIX + "The item doesn't contain" +
                                                    " the enchantment to remove!");
                                    } else
                                        player.sendMessage(Main.PREFIX + "Unknown enchantment!");
                                } catch (NumberFormatException exception) {
                                    player.sendMessage(Main.PREFIX + "Please enter a valid number!");
                                }
                            } else {
                                try {
                                    int level = 32767;
                                    if (!args[2].equalsIgnoreCase("max"))
                                        level = Integer.parseInt(args[2]);

                                    if (args[1].equalsIgnoreCase("all"))
                                        for (Enchantment enchantment : Enchantment.values())
                                            target.getInventory().getItemInMainHand()
                                                    .addUnsafeEnchantment(enchantment, level);
                                    else
                                        for (ItemStack itemStack : target.getInventory().getContents()) {
                                            if (itemStack != null) {
                                                ItemMeta itemMeta = itemStack.getItemMeta();
                                                itemMeta.setUnbreakable(true);
                                                itemStack.setItemMeta(itemMeta);
                                                for (Enchantment enchantment : Enchantment.values())
                                                    itemStack.addUnsafeEnchantment(enchantment, level);
                                            }
                                        }

                                    player.sendMessage(Main.PREFIX + "§aSuccessfully enchanted with" +
                                            " all enchantments!");
                                } catch (NumberFormatException exception) {
                                    player.sendMessage(Main.PREFIX + "Please enter a valid number!");
                                }
                            }
                        else
                            player.sendMessage(Main.PREFIX + "You can not enchant a hand!");
                    else
                        player.sendMessage(Main.PREFIX + "Player not found!");
                } else
                    return false;
            } else
                player.sendMessage(Main.NO_PERMISSION);
        } else if (args.length == 3) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null)
                if (!target.getInventory().getItemInMainHand().getType().equals(Material.AIR))
                    if (!args[1].equalsIgnoreCase("all")) {
                        try {

                            NamespacedKey enchantmentKey = NamespacedKey.minecraft(args[1]);
                            Enchantment enchantment = Enchantment.getByKey(enchantmentKey);

                            int level = 32767;
                            if (!(args[2].equalsIgnoreCase("max")
                                    || args[2].equalsIgnoreCase("nothing")))
                                level = Integer.parseInt(args[2]);

                            if (enchantment != null) {
                                if (!args[2].equalsIgnoreCase("nothing")) {
                                    target.getInventory().getItemInMainHand().addUnsafeEnchantment(enchantment
                                            , level);
                                    sender.sendMessage("Successfully enchanted item!");
                                } else if (target.getInventory().getItemInMainHand()
                                        .containsEnchantment(enchantment)) {
                                    target.getInventory().getItemInMainHand().removeEnchantment(enchantment);
                                    sender.sendMessage("Successfully removed enchantment!");
                                } else
                                    sender.sendMessage("The item doesn't contain the enchantment to " +
                                            "remove!");
                            } else
                                sender.sendMessage("Unknown enchantment!");
                        } catch (NumberFormatException exception) {
                            sender.sendMessage("Please enter a valid number!");
                        }
                    } else {
                        try {
                            int level = 32767;
                            if (!args[2].equalsIgnoreCase("max"))
                                level = Integer.parseInt(args[2]);

                            if (args[1].equalsIgnoreCase("all"))
                                for (Enchantment enchantment : Enchantment.values())
                                    target.getInventory().getItemInMainHand()
                                            .addUnsafeEnchantment(enchantment, level);
                            else
                                for (ItemStack itemStack : target.getInventory().getContents()) {
                                    if (itemStack != null) {
                                        ItemMeta itemMeta = itemStack.getItemMeta();
                                        itemMeta.setUnbreakable(true);
                                        itemStack.setItemMeta(itemMeta);
                                        for (Enchantment enchantment : Enchantment.values())
                                            itemStack.addUnsafeEnchantment(enchantment, level);
                                    }
                                }

                            sender.sendMessage("Successfully enchanted item with all enchantments!");
                        } catch (NumberFormatException exception) {
                            sender.sendMessage("Please enter a valid number!");
                        }
                    }
                else
                    sender.sendMessage("You can not enchant a hand!");
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
        else if (args.length == 2) {
            for (Enchantment enchantment : Enchantment.values())
                list.add(enchantment.getKey().getKey());
            list.add("all");
            list.add("inv");
        } else if (args.length == 3) {
            list.add("max");
            if (!args[1].equalsIgnoreCase("all"))
                list.add("nothing");
        }
        return list;
    }
}