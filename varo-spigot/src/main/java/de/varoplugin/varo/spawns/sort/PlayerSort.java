package de.varoplugin.varo.spawns.sort;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.varoplugin.cfw.player.SafeTeleport;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.spawns.Spawn;
import io.github.almightysatan.slams.Placeholder;

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
                SafeTeleport.tp(player, toTeleport.get(player));
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
            Messages.GAME_SORT_SPECTATOR_TELEPORT.send(vp);
            players.remove(vp);
        }

        for (Spawn spawn : spawnsForIterator) {
            if (spawn.getPlayer() == null || !spawn.getPlayer().isOnline())
                continue;

            spawn.getPlayer().cleanUpPlayer();
            toTeleport.put(spawn.getPlayer().getPlayer(), spawn.getLocation());
            Messages.GAME_SORT_PLAYER.send(spawn.getPlayer());
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
            Messages.GAME_SORT_NUMBER.send(player, Placeholder.constant("spawn-id", String.valueOf(spawn.getNumber())));
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
                            teamPlayer.cleanUpPlayer();
                            toTeleport.put(teamPlayer.getPlayer(), spawns.get(0).getLocation());
                            spawns.get(0).setPlayer(teamPlayer);
                            Messages.GAME_SORT_NUMBER.send(teamPlayer, Placeholder.constant("spawn-id", String.valueOf(spawn.getNumber())));
                            players.remove(teamPlayer);
                        }

                        spawns.remove(0);
                        playerTeamRegistered++;
                    } else {
                        result = SortResult.NO_SPAWN_WITH_TEAM;
                        players.remove(teamPlayer);
                        Messages.GAME_SORT_NO_SPAWN_FOUND_TEAM.send(teamPlayer);
                    }
                } else if (players.contains(teamPlayer)) {
                    teamPlayer.cleanUpPlayer();
                    toTeleport.put(teamPlayer.getPlayer(), spawns.get(0).getLocation());
                    spawns.get(0).setPlayer(teamPlayer);
                    Messages.GAME_SORT_NUMBER.send(teamPlayer, Placeholder.constant("spawn-id", String.valueOf(spawn.getNumber())));
                    players.remove(teamPlayer);
                    spawns.remove(0);
                }
            }
        }

        for (VaroPlayer vp : players) {
            Messages.GAME_SORT_NO_SPAWN_FOUND.send(vp);
            if (result == SortResult.SORTED_WELL) {
                result = SortResult.NO_SPAWN;
            }
        }

        startTeleporting();
        return result;
    }
}