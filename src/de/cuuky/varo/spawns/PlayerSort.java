package de.cuuky.varo.spawns;

import de.cuuky.cfw.utils.BukkitUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.spawns.Spawn;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;

public class PlayerSort {

    public enum SortResult {
        NO_SPAWN,
        NO_SPAWN_WITH_TEAM,
        SORTED_WELL
    }

    private BukkitTask scheduler;
    private HashMap<Player, Location> toTeleport;

    public PlayerSort() {
        toTeleport = new HashMap<>();
    }

    private void startTeleporting() {
        scheduler = new BukkitRunnable() {

            int index = 0;

            @Override
            public void run() {
                if (index == toTeleport.size()) {
                    toTeleport.clear();
                    scheduler.cancel();
                    return;
                }

                Player player = (Player) toTeleport.keySet().toArray()[index];
                BukkitUtils.saveTeleport(player, toTeleport.get(player));
                index++;
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);
    }

    public SortResult sortPlayers() {
        List<VaroPlayer> players = VaroPlayer.getOnlinePlayer();
        List<VaroPlayer> playersForIterator = VaroPlayer.getOnlinePlayer();
        List<Spawn> spawns = Spawn.getSpawnsClone();
        List<Spawn> spawnsForIterator = Spawn.getSpawns();

        for (VaroPlayer vp : playersForIterator) {
            if (!vp.getStats().isSpectator())
                continue;

            toTeleport.put(vp.getPlayer(), vp.getPlayer().getWorld().getSpawnLocation());
            vp.sendMessage(ConfigMessages.SORT_SPECTATOR_TELEPORT, vp);
            players.remove(vp);
        }

        for (Spawn spawn : spawnsForIterator) {
            if (spawn.getPlayer() == null || !spawn.getPlayer().isOnline())
                continue;

            spawn.getPlayer().cleanUpPlayer();
            toTeleport.put(spawn.getPlayer().getPlayer(), spawn.getLocation());
            spawn.getPlayer().sendMessage(ConfigMessages.SORT_OWN_HOLE);
            players.remove(spawn.getPlayer());
            spawns.remove(spawn);
        }

        SortResult result = SortResult.SORTED_WELL;
        while (spawns.size() > 0) {
            if (players.size() <= 0)
                break;

            VaroPlayer player = players.get(0);
            Spawn spawn = spawns.get(0);
            player.cleanUpPlayer();
            toTeleport.put(player.getPlayer(), spawn.getLocation());
            spawn.setPlayer(player);
            player.sendMessage(ConfigMessages.SORT_NUMBER_HOLE)
                .replace("%number%", String.valueOf(spawn.getId()));
            players.remove(0);
            spawns.remove(0);

            if (player.getTeam() == null)
                continue;

            int playerTeamRegistered = 1;
            for (VaroPlayer teamPlayer : player.getTeam().getMember()) {
                if (spawns.size() <= 0) {
                    break;
                }

                if (ConfigSetting.TEAM_PLACE_SPAWN.getValueAsInt() > 0) {
                    if (playerTeamRegistered < ConfigSetting.TEAM_PLACE_SPAWN.getValueAsInt()) {
                        if (players.contains(teamPlayer)) {
                            sort(players, spawns, teamPlayer);
                        }

                        spawns.remove(0);
                        playerTeamRegistered++;
                    } else {
                        result = SortResult.NO_SPAWN_WITH_TEAM;
                        players.remove(teamPlayer);
                        teamPlayer.sendMessage(ConfigMessages.SORT_NO_HOLE_FOUND_TEAM);
                    }
                } else if (players.contains(teamPlayer)) {
                    sort(players, spawns, teamPlayer);
                    spawns.remove(0);
                }
            }
        }

        for (VaroPlayer vp : players) {
            vp.sendMessage(ConfigMessages.SORT_NO_HOLE_FOUND);
            if (result == SortResult.SORTED_WELL) {
                result = SortResult.NO_SPAWN;
            }
        }

        startTeleporting();
        return result;
    }

    private void sort(List<VaroPlayer> players, List<Spawn> spawns, VaroPlayer teamPlayer) {
        teamPlayer.cleanUpPlayer();
        toTeleport.put(teamPlayer.getPlayer(), spawns.get(0).getLocation());
        spawns.get(0).setPlayer(teamPlayer);
        teamPlayer.sendMessage(ConfigMessages.SORT_NUMBER_HOLE)
            .replace("%number%", String.valueOf(spawns.get(0).getId()));
        players.remove(teamPlayer);
    }
}