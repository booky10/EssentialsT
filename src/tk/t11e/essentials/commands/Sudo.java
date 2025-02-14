package tk.t11e.essentials.commands;
// Created by booky10 in EssentialsT (19:51 12.01.20)

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import tk.t11e.essentials.main.Main;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"NullableProblems"})
public class Sudo implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("sudo"))
                if(args.length>=2) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if(target!=null) {
                        StringBuilder message= new StringBuilder();
                        for (int i = 1; i < args.length; i++)
                            message.append(args[i]);
                        target.chat(message.toString());
                        player.sendMessage(Main.PREFIX+"§aSuccessfully executed chat message!");
                    }else
                        player.sendMessage(Main.PREFIX+"Player not found!");
                }else
                    return false;
             else
                player.sendMessage(Main.NO_PERMISSION);
        } else
        if(args.length>=2) {
            Player target = Bukkit.getPlayer(args[0]);
            if(target!=null) {
                StringBuilder message= new StringBuilder();
                for (int i = 1; i < args.length; i++)
                    message.append(args[i]).append(" ");
                target.chat(message.toString());
                sender.sendMessage("Successfully executed chat message!");
            }else
                sender.sendMessage("Player not found!");
        }else
            return false;
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = new ArrayList<>();
        if(args.length==1)
            for (Player player : Bukkit.getOnlinePlayers())
                list.add(player.getName());
        return tk.t11e.essentials.util.TabCompleter.convert(args,list);
    }
}