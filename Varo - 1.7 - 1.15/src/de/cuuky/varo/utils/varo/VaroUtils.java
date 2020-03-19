package de.cuuky.varo.utils.varo;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.World;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.VaroTeam;
import de.cuuky.varo.spawns.Spawn;

public final class VaroUtils {

	public enum SortResult {
		NO_SPAWN,
		NO_SPAWN_WITH_TEAM,
		SORTED_WELL;
	}

	private static int worldToTimeID = 0;

	public static void setWorldToTime() {
		if(!ConfigSetting.ALWAYS_TIME.isIntActivated())
			return;

		if(worldToTimeID != 0) 
			Bukkit.getScheduler().cancelTask(worldToTimeID);

		worldToTimeID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

			int time = ConfigSetting.ALWAYS_TIME.getValueAsInt();

			@Override
			public void run() {
				if(Main.getVaroGame().hasStarted() && !ConfigSetting.ALWAYS_TIME_USE_AFTER_START.getValueAsBoolean()) {
					Bukkit.getScheduler().cancelTask(worldToTimeID);
					return;
				}

				for(World world : Bukkit.getWorlds()) {
					world.setTime(time);
					world.setThundering(false);
					world.setStorm(false);
				}
			}
		}, 0, 40);
	}

	public static SortResult sortPlayers() {
		ArrayList<VaroPlayer> players = VaroPlayer.getOnlinePlayer();
		ArrayList<VaroPlayer> playersForIterator = VaroPlayer.getOnlinePlayer();
		ArrayList<Spawn> spawns = Spawn.getSpawnsClone();
		ArrayList<Spawn> spawnsForIterator = Spawn.getSpawns();

		for(VaroPlayer vp : playersForIterator) {
			if(!vp.getStats().isSpectator()) 
				continue;

			vp.getPlayer().teleport(vp.getPlayer().getWorld().getSpawnLocation());
			vp.sendMessage(Main.getPrefix() + ConfigMessages.SORT_SPECTATOR_TELEPORT.getValue());
			players.remove(vp);
		}

		for(Spawn spawn : spawnsForIterator) {
			if(spawn.getPlayer() == null) {
				continue;
			} else if(!spawn.getPlayer().isOnline()) {
				continue;
			} else {
				spawn.getPlayer().cleanUpPlayer();
				spawn.getPlayer().getPlayer().teleport(spawn.getLocation());
				spawn.getPlayer().sendMessage(Main.getPrefix() + ConfigMessages.SORT_OWN_HOLE.getValue());
				players.remove(spawn.getPlayer());
				spawns.remove(spawn);
			}
		}

		SortResult result = SortResult.SORTED_WELL;

		while(spawns.size() > 0) {
			if(players.size() <= 0) 
				break;

			VaroPlayer player = players.get(0);
			Spawn spawn = spawns.get(0);
			player.cleanUpPlayer();
			player.getPlayer().teleport(spawn.getLocation());
			spawn.setPlayer(player);
			player.sendMessage(Main.getPrefix() + ConfigMessages.SORT_NUMBER_HOLE.getValue().replace("%number%", String.valueOf(spawn.getNumber())));
			players.remove(0);
			spawns.remove(0);

			if(player.getTeam() == null) 
				continue;

			int playerTeamRegistered = 1;
			for(VaroPlayer teamPlayer : player.getTeam().getMember()) {
				if(spawns.size() <= 0) {
					break;
				}

				if(ConfigSetting.TEAM_PLACE_SPAWN.getValueAsInt() > 0) {
					if(playerTeamRegistered < ConfigSetting.TEAM_PLACE_SPAWN.getValueAsInt()) {
						if(players.contains(teamPlayer)) {
							teamPlayer.cleanUpPlayer();
							teamPlayer.getPlayer().teleport(spawns.get(0).getLocation());
							spawns.get(0).setPlayer(teamPlayer);
							teamPlayer.sendMessage(Main.getPrefix() + ConfigMessages.SORT_NUMBER_HOLE.getValue().replace("%number%", String.valueOf(spawns.get(0).getNumber())));
							players.remove(teamPlayer);
						}

						spawns.remove(0);
						playerTeamRegistered++;
					} else {
						result = SortResult.NO_SPAWN_WITH_TEAM;
						players.remove(teamPlayer);
						teamPlayer.sendMessage(Main.getPrefix() + ConfigMessages.SORT_NO_HOLE_FOUND_TEAM.getValue());
					}
				} else if(players.contains(teamPlayer)) {
					teamPlayer.cleanUpPlayer();
					teamPlayer.getPlayer().teleport(spawns.get(0).getLocation());
					spawns.get(0).setPlayer(teamPlayer);
					teamPlayer.sendMessage(Main.getPrefix() + ConfigMessages.SORT_NUMBER_HOLE.getValue().replace("%number%", String.valueOf(spawns.get(0).getNumber())));
					players.remove(teamPlayer);
					spawns.remove(0);
				}
			}
		}

		for(VaroPlayer vp : players) {
			vp.sendMessage(Main.getPrefix() + ConfigMessages.SORT_NO_HOLE_FOUND.getValue());
			if(result == SortResult.SORTED_WELL) {
				result = SortResult.NO_SPAWN;
			}
		}

		return result;
	}

	public static void doRandomTeam(int teamSize) {
		if(teamSize >= 2) {
			ArrayList<VaroPlayer> finished = new ArrayList<>();
			for(VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
				if(finished.contains(vp) || vp.getStats().isSpectator() || vp.getTeam() != null)
					continue;

				ArrayList<VaroPlayer> teamMember = new ArrayList<>();
				teamMember.add(vp);
				finished.add(vp);

				int missingMember = teamSize - 1;
				for(VaroPlayer othervp : VaroPlayer.getOnlinePlayer()) {
					if(missingMember == 0)
						break;

					if(finished.contains(othervp) || othervp.getStats().isSpectator() || othervp.getTeam() != null)
						continue;

					teamMember.add(othervp);
					finished.add(othervp);
					missingMember--;
				}

				if(teamMember.size() != teamSize)
					vp.getPlayer().sendMessage(Main.getPrefix() + "§7Fuer dich wurden nicht genug" + Main.getColorCode() + " Teampartner §7gefunden!");

				String teamName = "";
				for(VaroPlayer teamPl : teamMember)
					teamName = teamName + teamPl.getName().substring(0, teamPl.getName().length() / teamSize);

				VaroTeam team = new VaroTeam(teamName);
				for(VaroPlayer teamPl : teamMember)
					team.addMember(teamPl);
			}
		} else if(teamSize == 1) {
			for(VaroPlayer pl : VaroPlayer.getOnlinePlayer()) {
				if(pl.getTeam() != null || pl.getStats().isSpectator())
					continue;

				new VaroTeam(pl.getName().length() == 16 ? pl.getName().substring(0, 15) : pl.getName()).addMember(pl);
			}
		}
	}
}