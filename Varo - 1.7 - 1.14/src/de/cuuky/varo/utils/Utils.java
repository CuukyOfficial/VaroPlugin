package de.cuuky.varo.utils;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.spawns.Spawn;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Utils {

	private Utils() {}

	public static ArrayList<Object> getAsList(String[] lis) {
		ArrayList<Object> list = new ArrayList<>();
		for(Object u : lis)
			list.add(u);

		return list;
	}

	public static String[] getAsArray(ArrayList<String> string) {
		String[] list = new String[string.size()];
		for(int i = 0; i < string.size(); i++)
			list[i] = string.get(i);

		return list;
	}

	public static Object getStringObject(String obj) {
		try {
			return Integer.parseInt(obj);
		} catch(NumberFormatException e) {}

		try {
			return Long.parseLong(obj);
		} catch(NumberFormatException e) {}

		try {
			return Double.parseDouble(obj);
		} catch(NumberFormatException e) {}

		if(obj.equalsIgnoreCase("true") || obj.equalsIgnoreCase("false"))
			return obj.equalsIgnoreCase("true") ? true : false;
		else
			return obj;
	}

	public static String getArgsToString(String[] args, String insertBewteen) {
		String command = "";
		for(String arg : args)
			if(command.equals(""))
				command = arg;
			else
				command = command + insertBewteen + arg;

		return command;
	}

	public static String getArgsToString(ArrayList<String> args, String insertBewteen) {
		String command = "";
		for(String arg : args)
			if(command.equals(""))
				command = arg;
			else
				command = command + insertBewteen + arg;

		return command;
	}

	public static String replaceAllColors(String s) {
		String newMessage = "";
		boolean lastPara = false;
		for(char c : s.toCharArray()) {
			if(lastPara) {
				lastPara = false;
				continue;
			}

			if(c == '�' || c == '&') {
				lastPara = true;
				continue;
			}

			newMessage = newMessage.isEmpty() ? String.valueOf(c) : newMessage + c;
		}

		return newMessage;
	}

	public static String[] removeString(String[] string, int loc) {
		String[] ret = new String[string.length - 1];
		int i = 0;
		boolean removed = false;
		for(String arg : string) {
			if(i == loc && !removed) {
				removed = true;
				continue;
			}

			ret[i] = arg;
			i++;
		}

		return ret;
	}

	public static int getNextToNine(int to) {
		int offset = 0;
		while(true) {
			int temp = to + offset;
			if(temp % 9 == 0)
				return temp;
			if(temp >= 54)
				return 54;

			if(temp <= 9)
				return 9;

			temp = to - offset;
			if(temp % 9 == 0)
				return temp;
			if(temp >= 54)
				return 54;

			if(temp <= 9)
				return 9;

			offset++;
		}
	}

	/**
	 * @param min
	 *            The minimum Range
	 * @param max
	 *            The maximum Range
	 * @return Returns a random Integer between the min and the max range
	 */
	public static int randomInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public static String[] arrayToCollection(List<String> strings) {
		String[] newStrings = new String[strings.size()];

		for(int i = 0; i < strings.size(); i++)
			newStrings[i] = strings.get(i);

		return newStrings;
	}

	public static ArrayList<String> collectionToArray(String[] strings) {
		ArrayList<String> newStrings = new ArrayList<>();
		for(String string : strings)
			newStrings.add(string);

		return newStrings;
	}

	public static String[] addIntoEvery(String[] input, String into, boolean start) {
		for(int i = 0; i < input.length; i++)
			input[i] = (start ? into + input[i] : input[i] + into);

		return input;
	}

	public static ArrayList<String> addIntoEvery(ArrayList<String> input, String into, boolean start) {
		for(int i = 0; i < input.size(); i++)
			input.set(i, (start ? into + input.get(i) : input.get(i) + into));

		return input;
	}

	public static String[] combineArrays(String[]... strings) {
		ArrayList<String> string = new ArrayList<>();

		for(String[] ss : strings)
			for(String strin : ss)
				string.add(strin);

		return Utils.getAsArray(string);
	}

	public enum sortResult {
		SORTED_WELL,
		NO_SPAWN_WITH_TEAM,
		NO_SPAWN;
	}

	public static sortResult sortPlayers() {
		ArrayList<VaroPlayer> players = VaroPlayer.getOnlinePlayer();
		ArrayList<Spawn> spawns = Spawn.getSpawnsClone();

		for(VaroPlayer vp : players) {
			if(!vp.getStats().isSpectator()) {
				continue;
			}
			vp.getPlayer().teleport(vp.getPlayer().getWorld().getSpawnLocation());
			vp.sendMessage(Main.getPrefix() + ConfigMessages.SORT_SPECTATOR_TELEPORT.getValue());
			players.remove(vp);
		}

		for(Spawn spawn : spawns) {
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
			if (result == sortResult.SORTED_WELL) {
				result = sortResult.NO_SPAWN;
			}
		}

		return result;
	}

	public static String formatLocation(Location location, String unformatted) {
		return unformatted.replaceAll("x", String.valueOf(location.getBlockX())).replaceAll("y", String.valueOf(location.getBlockY())).replaceAll("z", String.valueOf(location.getBlockZ())).replaceAll("world", location.getWorld().getName());
	}
}