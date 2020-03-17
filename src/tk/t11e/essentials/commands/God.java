package tk.t11e.essentials.commands;
// Created by booky10 in EssentialsT (19:51 12.01.20)

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.t11e.essentials.main.Main;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"StatementWithEmptyBody", "NullableProblems"})
public class God implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("god")) {
                if (args.length == 1) {
                    boolean invulnerability = false;
                    if (args[0].equalsIgnoreCase("on")
                            || args[0].equalsIgnoreCase("true"))
                        invulnerability = true;
                    else if (args[0].equalsIgnoreCase("off")
                            || args[0].equalsIgnoreCase("false")) {
                    } else
                        return false;
                    player.setInvulnerable(invulnerability);
                    player.sendMessage(Main.PREFIX + "§aSuccessfully set your invulnerability!");
                } else if (args.length == 2) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target != null) {
                        boolean invulnerability = false;
                        if (args[0].equalsIgnoreCase("on")
                                || args[0].equalsIgnoreCase("true"))
                            invulnerability = true;
                        else if (args[0].equalsIgnoreCase("off")
                                || args[0].equalsIgnoreCase("false")) {
                        } else
                            return false;
                        target.setInvulnerable(invulnerability);
                        player.sendMessage(Main.PREFIX + "§aSuccessfully set invulnerability!");
                    }
                } else
                    return false;
            } else
                player.sendMessage(Main.NO_PERMISSION);
        } else if (args.length == 2) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target != null) {
                boolean invulnerability = false;
                if (args[0].equalsIgnoreCase("on")
                        || args[0].equalsIgnoreCase("true"))
                    invulnerability = true;
                else if (args[0].equalsIgnoreCase("off")
                        || args[0].equalsIgnoreCase("false")) {
                } else
                    return false;
                target.setInvulnerable(invulnerability);
                sender.sendMessage("Successfully set invulnerability!");
            }
        } else
            return false;
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = new ArrayList<>();
        if (sender instanceof Player) {
            if (args.length == 1)
                if (((Player) sender).isInvulnerable())
                    list.add("off");
                else
                    list.add("on");
            else if (args.length == 2)
                for (Player player : Bukkit.getOnlinePlayers())
                    list.add(player.getName());
        } else if (args.length == 1) {
            list.add("off");
            list.add("on");
        } else if (args.length == 2)
            for (Player player : Bukkit.getOnlinePlayers())
                list.add(player.getName());
        return tk.t11e.essentials.util.TabCompleter.convert(args,list);
    }
}