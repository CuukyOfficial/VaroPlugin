package de.cuuky.varo.scoreboard;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import de.cuuky.varo.config.DefaultReplace;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.Team;
import de.cuuky.varo.version.BukkitVersion;
import de.cuuky.varo.version.VersionUtils;

public class ScoreboardHandler {

	private ArrayList<String> scoreboardLines;
	private HashMap<Player, ArrayList<String>> replaces;

	private String header;
	private TopScoreList topScores;

	public ScoreboardHandler() {
		loadScores();
	}

	public void loadScores() {
		scoreboardLines = new ArrayList<>();
		replaces = new HashMap<>();
		File file = new File("plugins/Varo", "scoreboard.yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		ArrayList<String> sb = new ArrayList<>();
		cfg.options().header(getHeader());
		sb.add("%space%");
		sb.add("&7Team&8:");
		sb.add("&3%team%");
		sb.add("%space%");
		sb.add("&7Kills&8:");
		sb.add("&3%kills%");
		sb.add("%space%");
		sb.add("&7Zeit:");
		sb.add("&3%min%&8:&3%sec%");
		sb.add("%space%");
		sb.add("&7Online: &3%online%");
		sb.add("&7Remaining: &3%remaining%");
		sb.add("&7Players: &3%players%");
		sb.add("%space%");

		if(!cfg.contains("Scoreboard"))
			cfg.set("Scoreboard", sb);
		try {
			cfg.save(file);
		} catch(IOException e) {
			e.printStackTrace();
		}

		scoreboardLines.addAll(cfg.getStringList("Scoreboard"));
		Collections.reverse(scoreboardLines);

		this.header = ConfigMessages.SCOREBOARD_HEADER.getValue().replaceAll("&", "§");
		this.topScores = new TopScoreList();

		String space = "";
		for(int i = 0; i < scoreboardLines.size(); i++) {
			String line = scoreboardLines.get(i);
			if(line.equals("%space%")) {
				space += " ";
				scoreboardLines.set(i, space);
			}
		}
	}

	private String getConvString(String line, VaroPlayer vp, Scoreboard sb) {
		if(line.contains("%min%") || line.contains("%sec%"))
			if(ConfigEntry.PLAY_TIME.getValueAsInt() < 1)
				return "§cUnlimited";

		for(int rank : getConvNumbers(line, "%topplayer-")) {
			VaroPlayer player = topScores.getPlayer(rank);
			line = line.replace("%topplayer-" + rank + "%", (player == null ? "-" : player.getName()));
		}

		for(int rank : getConvNumbers(line, "%topplayerkills-")) {
			VaroPlayer player = topScores.getPlayer(rank);
			line = line.replace("%topplayerkills-" + rank + "%", (player == null ? "0" : String.valueOf(player.getStats().getKills())));
		}

		for(int rank : getConvNumbers(line, "%topteam-")) {
			Team team = topScores.getTeam(rank);
			line = line.replace("%topteam-" + rank + "%", (team == null ? "-" : team.getName()));
		}

		for(int rank : getConvNumbers(line, "%topteamkills-")) {
			Team team = topScores.getTeam(rank);
			line = line.replace("%topteamkills-" + rank + "%", (team == null ? "0" : String.valueOf(team.getKills())));
		}

		line = new DefaultReplace(line).getReplaced(vp);

		if(VersionUtils.getVersion() == BukkitVersion.ONE_7 && line.length() > 16)
			line = line.substring(0, 16);
		else if(line.length() > 40)
			line = line.substring(0, 40);

		return line;
	}

	private ArrayList<Integer> getConvNumbers(String line, String key) {
		ArrayList<Integer> list = new ArrayList<>();

		boolean first = true;
		for(String split0 : line.split(key)) {
			if(first) {
				first = false;
				if(!line.startsWith(key))
					continue;
			}

			String[] split1 = split0.split("%", 2);

			if(split1.length == 2) {
				try {
					list.add(Integer.parseInt(split1[0]));
				} catch(NumberFormatException e) {
					continue;
				}
			}
		}

		return list;
	}

	public void sendScoreBoard(VaroPlayer vp) {
		if(!ConfigEntry.SCOREBOARD.getValueAsBoolean() || !vp.getStats().isShowScoreboard())
			return;

		Player player = vp.getPlayer();
		if(player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null)
			return;

		Scoreboard sb = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		Objective obj = sb.registerNewObjective(getConvString(header, vp, sb), "dummy");

		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		player.setScoreboard(sb);

		replaces.remove(player);
		update(vp);
	}

	public void update(VaroPlayer player) {
		ArrayList<String> replacesLst = replaces.get(player.getPlayer());
		if(replacesLst == null)
			replaces.put(player.getPlayer(), replacesLst = new ArrayList<>());

		Scoreboard board = player.getPlayer().getScoreboard();
		Objective obj = board.getObjective(DisplaySlot.SIDEBAR);

		if(obj == null) {
			sendScoreBoard(player);
			return;
		}

		for(int index = 0; index < scoreboardLines.size(); index++) {
			String line = getConvString(scoreboardLines.get(index), player, board);

			if(replacesLst.size() < scoreboardLines.size()) {
				obj.getScore(line).setScore(index);
				replacesLst.add(line);
			} else if(!replacesLst.get(index).equals(line)) {
				board.resetScores(replacesLst.get(index));
				obj.getScore(line).setScore(index);
				replacesLst.set(index, line);
			}
		}
	}
	
	public void updateList() {
		loadScores();
	}

	public void updateTopScores() {
		topScores.update();
	}

	public String getHeader() {
		return "Die Liste aller Placeholder steht auf der Seite des Plugins!";
	}

	public TopScoreList getTopPlayers() {
		return topScores;
	}
}