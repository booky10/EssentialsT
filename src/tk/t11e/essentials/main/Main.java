package tk.t11e.essentials.main;
// Created by booky10 in EssentialsT (15:05 26.12.19)

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import tk.t11e.essentials.commands.*;

import java.util.Objects;

public class Main extends JavaPlugin {

    public static String NO_COLOR_PREFIX, PREFIX, NO_PERMISSION;

    @Override
    public void onEnable() {
        long milliseconds = System.currentTimeMillis();
        System.out.println("Starting initialisation...");

        saveDefaultConfig();

        NO_COLOR_PREFIX = getConfig().getString("Prefix");
        PREFIX = "§7[§b" + NO_COLOR_PREFIX + "§7]§c ";
        NO_PERMISSION = PREFIX + "You don't have the permissions for this!";

        initCommands();
        initListener(Bukkit.getPluginManager());

        System.out.println("Finished initialisation in " + (System.currentTimeMillis() - milliseconds)
                + " ms!");
    }

    @SuppressWarnings({"EmptyMethod", "unused"})
    private void initListener(PluginManager pluginManager) {
    }

    private void initCommands() {
        Objects.requireNonNull(getCommand("essentialsT")).setExecutor(new EssentialsT());
        Objects.requireNonNull(getCommand("gameMode")).setExecutor(new GameMode());
        Objects.requireNonNull(getCommand("gm")).setExecutor(new GameMode());
        Objects.requireNonNull(getCommand("enchant")).setExecutor(new Enchant());
        Objects.requireNonNull(getCommand("unEnchant")).setExecutor(new UnEnchant());
        Objects.requireNonNull(getCommand("god")).setExecutor(new God());
        Objects.requireNonNull(getCommand("fly")).setExecutor(new Fly());
    }
}