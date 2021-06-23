package de.cuuky.varo.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import net.labymod.serverapi.Permission;
import net.labymod.serverapi.bukkit.event.LabyModPlayerJoinEvent;
import net.labymod.serverapi.bukkit.event.PermissionsSendEvent;

public class PermissionSendListener implements Listener {

    private static Map<Player, Long> toCheck = new HashMap<>();
    private static BukkitTask task;

    private static void startChecking() {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : toCheck.keySet()) {
                    if (!player.isOnline()) {
                        toCheck.remove(player);
                        continue;
                    }

                    if (toCheck.get(player) + 3000 <= System.currentTimeMillis()) {
                        toCheck.remove(player);
                        player.kickPlayer("§cOnly labymod players allowed!");
                        continue;
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 40L, 20L);
    }

    public PermissionSendListener() {
        if (task == null)
            startChecking();
    }

    @EventHandler
    public void labyModJoin(LabyModPlayerJoinEvent event) {
        toCheck.remove(event.getPlayer());

        if (ConfigSetting.KICK_LABYMOD_PLAYER.getValueAsBoolean())
            new BukkitRunnable() {
                @Override
                public void run() {
                    VaroPlayer vp = VaroPlayer.getPlayer(event.getPlayer());
                    vp.getPlayer().kickPlayer(ConfigMessages.LABYMOD_KICK.getValue(vp, vp));
                }
            }.runTaskLater(Main.getInstance(), 5L);
    }

    @EventHandler
    public void onPermissionSend(PermissionsSendEvent event) {
        if (ConfigSetting.DISABLE_LABYMOD_FUNCTIONS.getValueAsBoolean())
            for (Entry<Permission, Boolean> permissionEntry : event.getPermissions().entrySet())
                permissionEntry.setValue(false);
    }

    public static void addCheck(Player player) {
        toCheck.put(player, System.currentTimeMillis());
    }
}