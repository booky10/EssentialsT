package tk.t11e.essentials.main;
// Created by booky10 in EssentialsT (15:05 26.12.19)

import me.dablakbandit.stoppushing.StopPushingPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import tk.t11e.essentials.commands.*;
import tk.t11e.essentials.crafting.Recipes;
import tk.t11e.essentials.listener.GlassItemFrameListener;
import tk.t11e.essentials.listener.SortingListener;
import tk.t11e.essentials.util.ExplosionRestorer;

import java.util.Objects;

public class Main extends JavaPlugin {

    public static String NO_COLOR_PREFIX, PREFIX, NO_PERMISSION;
    public static StopPushingPlugin stopPushingPlugin = new StopPushingPlugin();
    public static Main main;

    @Override
    public void onEnable() {
        long milliseconds = System.currentTimeMillis();
        main=this;

        stopPushingPlugin.onEnable();
        saveDefaultConfig();
        Recipes.register();

        NO_COLOR_PREFIX = getConfig().getString("Prefix");
        PREFIX = "§7[§b" + NO_COLOR_PREFIX + "§7]§c ";
        NO_PERMISSION = PREFIX + "You don't have the permissions for this!";

        initCommands();
        initListener(Bukkit.getPluginManager());

        milliseconds = System.currentTimeMillis() - milliseconds;
        System.out.println("[EssentialsT] It took " + milliseconds + "ms to initialize this plugin!");
    }

    @SuppressWarnings({"EmptyMethod", "unused"})
    private void initListener(PluginManager pluginManager) {
        Bukkit.getPluginManager().registerEvents(new StopPushingPlugin(), this);
        Bukkit.getPluginManager().registerEvents(new SortingListener(), this);
        Bukkit.getPluginManager().registerEvents(new ExplosionRestorer(), this);
        //Bukkit.getPluginManager().registerEvents(new GlassItemFrameListener(), this);
    }

    private void initCommands() {
        Objects.requireNonNull(getCommand("essentialst")).setExecutor(new EssentialsT());
        Objects.requireNonNull(getCommand("gamemode")).setExecutor(new GameMode());
        Objects.requireNonNull(getCommand("gm")).setExecutor(new GameMode());
        Objects.requireNonNull(getCommand("enchant")).setExecutor(new Enchant());
        Objects.requireNonNull(getCommand("unenchant")).setExecutor(new UnEnchant());
        Objects.requireNonNull(getCommand("god")).setExecutor(new God());
        Objects.requireNonNull(getCommand("fly")).setExecutor(new Fly());
        Objects.requireNonNull(getCommand("sudo")).setExecutor(new Sudo());
        Objects.requireNonNull(getCommand("geteffects")).setExecutor(new GetEffects());
        new ExplosionRestorer().init();
    }
}