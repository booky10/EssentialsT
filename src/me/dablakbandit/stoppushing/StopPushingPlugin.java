// 
// Decompiled by Procyon v0.5.36
// 

package me.dablakbandit.stoppushing;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import tk.t11e.essentials.main.Main;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Random;

@SuppressWarnings("deprecation")
public class StopPushingPlugin implements Listener
{
    private static String[] all;
    private static Random r;
    private static Class<?> ct;
    private static Field t;
    private static Class<?> etp;
    private static Class<?> st;
    private static Method a;
    private static Method k;
    private static Object ALLOW;
    private static Object NEVER;
    private static Field e;
    private static boolean old;
    private static boolean packet;
    private static Class<?> ppost;
    private static Field ppostf;
    
    static {
        StopPushingPlugin.all = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
        StopPushingPlugin.r = new Random();
        StopPushingPlugin.ct = NMSUtils.getOBCClassSilent("scoreboard.CraftTeam");
        StopPushingPlugin.t = NMSUtils.getFieldSilent(StopPushingPlugin.ct, "team");
        StopPushingPlugin.etp = NMSUtils.getNMSClassSilent("EnumTeamPush", "ScoreboardTeamBase");
        StopPushingPlugin.st = NMSUtils.getNMSClassSilent("ScoreboardTeam");
        StopPushingPlugin.a = NMSUtils.getMethodSilent(StopPushingPlugin.st, "a", StopPushingPlugin.etp);
        StopPushingPlugin.k = NMSUtils.getMethodSilent(StopPushingPlugin.st, "k");
        StopPushingPlugin.old = false;
        StopPushingPlugin.packet = false;
        try {
            final Class<?> test = NMSUtils.getInnerClassSilent(Team.class, "Option");
            StopPushingPlugin.etp = NMSUtils.getNMSClassSilent("EnumTeamPush", "ScoreboardTeamBase");
            StopPushingPlugin.ALLOW = StopPushingPlugin.etp.getEnumConstants()[0];
            StopPushingPlugin.NEVER = StopPushingPlugin.etp.getEnumConstants()[1];
            StopPushingPlugin.e = NMSUtils.getField(StopPushingPlugin.etp, "e");
            if (test == null) {
                StopPushingPlugin.old = true;
                StopPushingPlugin.ct = NMSUtils.getOBCClass("scoreboard.CraftTeam");
                StopPushingPlugin.t = NMSUtils.getField(StopPushingPlugin.ct, "team");
                StopPushingPlugin.st = NMSUtils.getNMSClass("ScoreboardTeam");
                StopPushingPlugin.a = NMSUtils.getMethod(Objects.requireNonNull(StopPushingPlugin.st), "a", StopPushingPlugin.etp);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        StopPushingPlugin.ppost = NMSUtils.getNMSClassSilent("PacketPlayOutScoreboardTeam");
        StopPushingPlugin.ppostf = NMSUtils.getFieldSilent(StopPushingPlugin.ppost, "f");
    }
    
    public void onEnable() {
                new INCChannelHandler();
                StopPushingPlugin.packet = true;
        Bukkit.getPluginManager().registerEvents(this, Main.getPlugin(Main.class));
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.getPlugin(Main.class), () -> {
            for (final Player player : Bukkit.getOnlinePlayers()) {
                StopPushingPlugin.this.set(player);
            }
        }, 200L, 200L);
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        this.set(event.getPlayer());
    }
    
    private void set(final Team team, final Player player) {
        if (player.hasPermission("stoppushing.allow")) {
            if (!team.getOption(Team.Option.COLLISION_RULE).equals(Team.OptionStatus.ALWAYS)) {
                team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.ALWAYS);
            }
        }
        else if (!team.getOption(Team.Option.COLLISION_RULE).equals(Team.OptionStatus.NEVER)) {
            team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
        }
    }
    
    private void setOld(final Team team, final Player player) {
        try {
            final Object o = StopPushingPlugin.t.get(team);
            final Object o2 = StopPushingPlugin.k.invoke(o);
            if (player.hasPermission("stoppushing.allow")) {
                if (!o2.equals(StopPushingPlugin.ALLOW)) {
                    StopPushingPlugin.a.invoke(o, StopPushingPlugin.ALLOW);
                }
            }
            else if (!o2.equals(StopPushingPlugin.NEVER)) {
                StopPushingPlugin.a.invoke(o, StopPushingPlugin.NEVER);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void set(final Player player) {
        Scoreboard sb = player.getScoreboard();
        final String name = player.getName();
        Team team = sb.getEntryTeam(name);
        if (team == null) {
            String n;
            for (n = name; sb.getTeam(n) != null; n = n.substring(1)) {
                n = n + StopPushingPlugin.all[StopPushingPlugin.r.nextInt(StopPushingPlugin.all.length - 1)];
            }
            team = sb.registerNewTeam(n);
            team.addEntry(name);
        }
        if (!StopPushingPlugin.packet) {
            if (StopPushingPlugin.old) {
                this.setOld(team, player);
            }
            else {
                this.set(team, player);
            }
        }
    }

    public static void parse(final Object object) {
        if (object.getClass().equals(StopPushingPlugin.ppost)) {
            try {
                StopPushingPlugin.ppostf.set(object, StopPushingPlugin.e.get(StopPushingPlugin.NEVER));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
