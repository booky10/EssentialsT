package tk.t11e.essentials.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import tk.t11e.api.commands.CommandExecutor;
import tk.t11e.essentials.main.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExplosionRestorer extends CommandExecutor implements Listener {

    private List<HashMap<Location, Material>> latestExplosions = new ArrayList<>();
    private List<HashMap<Location, Biome>> latestExplosionBiome = new ArrayList<>();
    private List<HashMap<Location, BlockData>> latestExplosionData = new ArrayList<>();

    public ExplosionRestorer() {
        super(Main.main, "restoreexplosion", "/restoreexplosion [amount]", "explosion" +
                ".restore", Receiver.ALL, "exrestore", "restoreex", "explosionrestore");
    }


    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            undoLastExplosion();
            sender.sendMessage("Successfully undid the last explosion!");
        } else if (args.length == 1)
            try {
                int amount;
                if (args[0].equalsIgnoreCase("all"))
                    amount = Integer.MAX_VALUE;
                else
                    amount = Integer.parseInt(args[0]);
                undoExplosions(amount);
                sender.sendMessage("Successfully undid the explosions!");
            } catch (NumberFormatException exception) {
                sender.sendMessage("Unknown number");
            }
        else
            help(sender);
    }

    @Override
    public void onPlayerExecute(Player player, String[] args) {
        if (args.length == 0) {
            undoLastExplosion();
            player.sendMessage(Main.PREFIX + "§aSuccessfully undid the last explosion!");
        } else if (args.length == 1)
            try {
                int amount;
                if (args[0].equalsIgnoreCase("all"))
                    amount = Integer.MAX_VALUE;
                else
                    amount = Integer.parseInt(args[0]);
                undoExplosions(amount);
                player.sendMessage(Main.PREFIX + "§aSuccessfully undid the latest explosions!");
            } catch (NumberFormatException exception) {
                player.sendMessage(Main.PREFIX + "Unknown number");
            }
        else
            System.out.println(latestExplosions.size());
    }

    @Override
    public List<String> onComplete(CommandSender sender, String[] args, List<String> list) {
        if (args.length == 1)
            list.add("all");
        return list;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityExplosion(EntityExplodeEvent event) {
        if (!event.isCancelled())
            logExplodedBlocks(event.blockList());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockExplosion(BlockExplodeEvent event) {
        if (!event.isCancelled())
            logExplodedBlocks(new ArrayList<>(event.blockList()));
    }

    private void logExplodedBlocks(List<Block> blocks) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.main, () -> {
            HashMap<Location, Material> latestExplosionMap = new HashMap<>();
            HashMap<Location, Biome> latestExplosionBiomeMap = new HashMap<>();
            HashMap<Location, BlockData> latestExplosionDataMap = new HashMap<>();
            for (Block block : blocks) {
                latestExplosionMap.put(block.getLocation(), block.getType());
                latestExplosionBiomeMap.put(block.getLocation(), block.getBiome());
                latestExplosionDataMap.put(block.getLocation(), block.getBlockData());
            }
            latestExplosions.add(latestExplosionMap);
            latestExplosionBiome.add(latestExplosionBiomeMap);
            latestExplosionData.add(latestExplosionDataMap);
            System.out.println(latestExplosions.size());
        });
    }

    private void undoLastExplosion() {
        Bukkit.getScheduler().runTaskAsynchronously(Main.main, () -> {
            System.out.println(latestExplosions.size());
            HashMap<Location, Material> latestExplosionMap =
                    latestExplosions.get(latestExplosions.size() - 1);
            for (Location location : latestExplosionMap.keySet())
                location.getBlock().setType(latestExplosionMap.get(location), false);
            HashMap<Location, Biome> latestExplosionBiomeMap =
                    latestExplosionBiome.get(latestExplosionBiome.size() - 1);
            for (Location location : latestExplosionBiomeMap.keySet())
                location.getBlock().setBiome(latestExplosionBiomeMap.get(location));
            HashMap<Location, BlockData> latestExplosionDataMap =
                    latestExplosionData.get(latestExplosionData.size() - 1);
            for (Location location : latestExplosionDataMap.keySet())
                location.getBlock().setBlockData(latestExplosionDataMap.get(location), false);
            latestExplosions.remove(latestExplosionMap);
            latestExplosionBiome.remove(latestExplosionBiomeMap);
            latestExplosionData.remove(latestExplosionDataMap);
        });
    }

    private void undoExplosions(int amounts) {
        if (amounts == 1)
            undoLastExplosion();
        else if (amounts >= latestExplosions.size())
            for (int i = 0; i < latestExplosions.size(); i++)
                undoLastExplosion();
        else if (amounts > 1)
            for (int i = 0; i < amounts; i++)
                undoLastExplosion();
    }
}