package de.cuuky.varo.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.configuration.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.VaroTeam;
import de.cuuky.varo.game.Game;
import de.cuuky.varo.spawns.Spawn;

public final class VaroUtils {

	public enum sortResult {
		NO_SPAWN,
		NO_SPAWN_WITH_TEAM,
		SORTED_WELL;
	}

	public enum UpdateResult {

		FAIL_SPIGOT("Es konnte keine Verbindung zum Server hergestellt werden."),
		NO_UPDATE("Das Plugin ist auf dem neuesten Stand!"),
		TEST_BUILD("Das Plugin ist auf dem neuesten Stand! (Test-Build)"),
		UPDATE_AVAILABLE("Es ist ein Update verfügbar! Benutze /varo update oder lade es manuell unter https://www.spigotmc.org/resources/71075/ herunter");

		private String message;

		UpdateResult(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}

	public enum VersionCompareResult {
		VERSION1GREATER,
		VERSION2GREATER,
		VERSIONS_EQUAL;
	}

	private static int worldToTimeID = 0;

	public static Object[] checkForUpdates() {
		UpdateResult result = UpdateResult.NO_UPDATE;
		String version;

		try {
			Scanner scanner = new Scanner(new URL("https://api.spiget.org/v2/resources/71075/versions/latest").openStream());
			String all = "";
			while(scanner.hasNextLine()) {
				all += scanner.nextLine();
			}
			scanner.close();

			JSONObject scannerJSON = (JSONObject) JSONValue.parseWithException(all);
			version = scannerJSON.get("name").toString();
			switch(compareVersions(version, Main.getInstance().getDescription().getVersion())) {
			case VERSION1GREATER:
				result = UpdateResult.UPDATE_AVAILABLE;
				break;
			case VERSIONS_EQUAL:
				result = UpdateResult.NO_UPDATE;
				break;
			case VERSION2GREATER:
				result = UpdateResult.TEST_BUILD;
				break;
			}
		} catch(Exception e) {
			result = UpdateResult.FAIL_SPIGOT;
			version = "";
		}
		return new Object[] { result, version };
	}

	public static VersionCompareResult compareVersions(String version1, String version2) {
		if(!version1.matches("[0-9]+(\\.[0-9]+)*") || !version2.matches("[0-9]+(\\.[0-9]+)*")) {
			throw new IllegalArgumentException("Invalid version format");
		}

		String[] version1Parts = version1.split("\\.");
		String[] version2Parts = version2.split("\\.");

		for(int i = 0; i < Math.max(version1Parts.length, version2Parts.length); i++) {
			int version1Part = i < version1Parts.length ? Integer.parseInt(version1Parts[i]) : 0;
			int version2Part = i < version2Parts.length ? Integer.parseInt(version2Parts[i]) : 0;
			if(version1Part < version2Part)
				return VersionCompareResult.VERSION2GREATER;
			if(version1Part > version2Part)
				return VersionCompareResult.VERSION1GREATER;
		}

		return VersionCompareResult.VERSIONS_EQUAL;
	}

	public static String formatLocation(Location location, String unformatted) {
		return unformatted.replace("x", String.valueOf(location.getBlockX())).replace("y", String.valueOf(location.getBlockY())).replace("z", String.valueOf(location.getBlockZ())).replace("world", location.getWorld().getName());
	}

	public static World getMainWorld() {
		return Bukkit.getWorld((String) VaroUtils.readServerProperties("level-name"));
	}

	public static Location getTeleportLocation() {
		return Game.getInstance().getLobby() != null ? Game.getInstance().getLobby() : getMainWorld().getSpawnLocation().add(0, 5, 0);
	}

	public static Object readServerProperties(String key) {
		try {
			Scanner scanner = new Scanner(new File("server.properties"));
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(!line.split("=")[0].equals(key))
					continue;

				scanner.close();
				return line.split("=")[1];
			}

			scanner.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void setWorldToTime() {
		if(!ConfigEntry.ALWAYS_TIME.isIntActivated())
			return;

		if(worldToTimeID != 0) {
			Bukkit.getScheduler().cancelTask(worldToTimeID);
		}

		worldToTimeID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

			int time = ConfigEntry.ALWAYS_TIME.getValueAsInt();

			@Override
			public void run() {
				if(Game.getInstance().hasStarted() && !ConfigEntry.ALWAYS_TIME_USE_AFTER_START.getValueAsBoolean()) {
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

	public static sortResult sortPlayers() {
		ArrayList<VaroPlayer> players = VaroPlayer.getOnlinePlayer();
		ArrayList<VaroPlayer> playersForIterator = VaroPlayer.getOnlinePlayer();
		ArrayList<Spawn> spawns = Spawn.getSpawnsClone();
		ArrayList<Spawn> spawnsForIterator = Spawn.getSpawns();

		for(VaroPlayer vp : playersForIterator) {
			if(!vp.getStats().isSpectator()) {
				continue;
			}

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

		sortResult result = sortResult.SORTED_WELL;

		while(spawns.size() > 0) {
			if(players.size() <= 0) {
				break;
			}

			VaroPlayer player = players.get(0);
			Spawn spawn = spawns.get(0);
			player.cleanUpPlayer();
			player.getPlayer().teleport(spawn.getLocation());
			player.sendMessage(Main.getPrefix() + ConfigMessages.SORT_NUMBER_HOLE.getValue().replace("%number%", String.valueOf(spawn.getNumber())));
			players.remove(0);
			spawns.remove(0);

			if(player.getTeam() == null) {
				continue;
			}

			int playerTeamRegistered = 1;
			for(VaroPlayer teamPlayer : player.getTeam().getMember()) {
				if(spawns.size() <= 0) {
					break;
				}

				if(ConfigEntry.TEAM_PLACE_SPAWN.getValueAsInt() > 0) {
					if(playerTeamRegistered < ConfigEntry.TEAM_PLACE_SPAWN.getValueAsInt()) {
						if(players.contains(teamPlayer)) {
							teamPlayer.cleanUpPlayer();
							teamPlayer.getPlayer().teleport(spawns.get(0).getLocation());
							teamPlayer.sendMessage(Main.getPrefix() + ConfigMessages.SORT_NUMBER_HOLE.getValue().replace("%number%", String.valueOf(spawns.get(0).getNumber())));
							players.remove(teamPlayer);
						}

						spawns.remove(0);
						playerTeamRegistered++;
					} else {
						result = sortResult.NO_SPAWN_WITH_TEAM;
						players.remove(teamPlayer);
						teamPlayer.sendMessage(Main.getPrefix() + ConfigMessages.SORT_NO_HOLE_FOUND_TEAM.getValue());
					}
				} else if(players.contains(teamPlayer)) {
					teamPlayer.cleanUpPlayer();
					teamPlayer.getPlayer().teleport(spawns.get(0).getLocation());
					teamPlayer.sendMessage(Main.getPrefix() + ConfigMessages.SORT_NUMBER_HOLE.getValue().replace("%number%", String.valueOf(spawns.get(0).getNumber())));
					players.remove(teamPlayer);
					spawns.remove(0);
				}
			}
		}

		for(VaroPlayer vp : players) {
			vp.sendMessage(Main.getPrefix() + ConfigMessages.SORT_NO_HOLE_FOUND.getValue());
			if(result == sortResult.SORTED_WELL) {
				result = sortResult.NO_SPAWN;
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
					vp.getPlayer().sendMessage(Main.getPrefix() + "§7Für dich wurden nicht genug" + Main.getColorCode() + " Teampartner §7gefunden!");

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