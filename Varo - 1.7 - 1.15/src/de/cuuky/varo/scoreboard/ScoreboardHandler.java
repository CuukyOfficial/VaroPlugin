package de.cuuky.varo.scoreboard;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.configuration.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.VaroTeam;

public class ScoreboardHandler {

	private String header;
	private HashMap<Player, ArrayList<String>> replaces;

	private ArrayList<String> scoreboardLines;
	private TopScoreList topScores;

	public ScoreboardHandler() {
		loadScores();
	}

	private String prepareScoreboardStatement(Scoreboard board, int index, String line) {
		Team team = board.getTeam("team-" + getAsTeam(index));
		if(team == null)
			team = board.registerNewTeam("team-" + getAsTeam(index));

		String playerName = getAsTeam(index);
		String[] pas = getPrefixAndSuffix(line);
		team.setPrefix(pas[0]);
		team.setSuffix(pas[1]);

		if(!team.getEntries().contains(playerName))
			team.addEntry(playerName);

		return playerName;
	}

	private String[] getPrefixAndSuffix(String value) {
		String prefix = getPrefix(value);
		String suffix = "";

		if(prefix.substring(prefix.length() - 1, prefix.length()).equals("§")) {
			prefix = prefix.substring(0, prefix.length() - 1);
			suffix = getPrefix("§" + getSuffix(value));
		} else
			suffix = getPrefix(ChatColor.getLastColors(prefix) + getSuffix(value));

		return new String[] { prefix, suffix };
	}

	private String getPrefix(String value) {
		return value.length() > 16 ? value.substring(0, 16) : value;
	}

	private String getSuffix(String value) {
		value = value.length() > 32 ? value.substring(0, 32) : value;

		return value.length() > 16 ? value.substring(16) : "";
	}

	private String getAsTeam(int index) {
		return ChatColor.values()[index].toString();
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

	private String getConvString(String line, VaroPlayer vp) {
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
			VaroTeam team = topScores.getTeam(rank);
			line = line.replace("%topteam-" + rank + "%", (team == null ? "-" : team.getName()));
		}

		for(int rank : getConvNumbers(line, "%topteamkills-")) {
			VaroTeam team = topScores.getTeam(rank);
			line = line.replace("%topteamkills-" + rank + "%", (team == null ? "0" : String.valueOf(team.getKills())));
		}

		line = ConfigMessages.getValue(line, vp);

		return line;
	}

	public String getHeader() {
		return "Die Liste aller Placeholder steht auf der Seite des Plugins!\nhttps://www.spigotmc.org/resources/71075/";
	}

	public TopScoreList getTopPlayers() {
		return topScores;
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
		sb.add("&7Zeit&8:");
		sb.add("&3%min%&8:&3%sec%");
		sb.add("%space%");
		sb.add("&7Online: &3%online%");
		sb.add("&7Remaining: &3%remaining%");
		sb.add("&7Players: &3%players%");
		sb.add("%space%");

		if(!cfg.contains("Scoreboard")) {
			cfg.set("Scoreboard", sb);
			try {
				cfg.save(file);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}

		scoreboardLines.addAll(cfg.getStringList("Scoreboard"));
		Collections.reverse(scoreboardLines);

		this.header = Main.getProjectName().replace("&", "§");
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

	public void sendScoreBoard(VaroPlayer vp) {
		if(!ConfigEntry.SCOREBOARD.getValueAsBoolean() || !vp.getStats().isShowScoreboard())
			return;

		Player player = vp.getPlayer();
		if(player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null)
			return;

		Scoreboard sb = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		Objective obj = sb.registerNewObjective("silent", "dummy");
		obj.setDisplayName(getConvString(header, vp));

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

		if(board.getEntries().size() > scoreboardLines.size()) {
			for(Team team : board.getTeams())
				if(team.getName().startsWith("team-"))
					team.unregister();

			for(String s : board.getEntries())
				board.resetScores(s);

			replacesLst.clear();
		}

		for(int index = 0; index < scoreboardLines.size(); index++) {
			String line = getConvString(scoreboardLines.get(index), player);

			if(replacesLst.size() < scoreboardLines.size()) {
				obj.getScore(prepareScoreboardStatement(board, index, line)).setScore(index + 1);
				replacesLst.add(line);
			} else if(!replacesLst.get(index).equals(line)) {
				String sbs = prepareScoreboardStatement(board, index, line);
				board.resetScores(sbs);
				obj.getScore(sbs).setScore(index + 1);
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
}