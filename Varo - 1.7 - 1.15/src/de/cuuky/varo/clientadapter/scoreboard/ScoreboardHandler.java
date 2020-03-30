package de.cuuky.varo.clientadapter.scoreboard;

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

import de.cuuky.varo.clientadapter.BoardHandler;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;

public class ScoreboardHandler implements BoardHandler {

	private String header;
	private HashMap<Player, String> headers;

	private HashMap<Player, ArrayList<String>> replaces;

	private ArrayList<String> scoreboardLines;

	public ScoreboardHandler() {
		updateList();
	}

	private String prepareScoreboardStatement(Scoreboard board, int index, String line) {
		String teamname = "team-" + getAsTeam(index);
		Team team = board.getTeam(teamname);
		if(team == null)
			team = board.registerNewTeam(teamname);

		String playerName = getAsTeam(index);
		String[] pas = getPrefixAndSuffix(line);
		team.setPrefix(pas[0]);
		team.setSuffix(pas[1]);
		team.addPlayer(new FakeOfflinePlayer(playerName));

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

	private String getConvString(String line, VaroPlayer vp) {
		if(line.contains("%min%") || line.contains("%sec%"))
			if(ConfigSetting.PLAY_TIME.getValueAsInt() < 1)
				return "§cUnlimited";

		line = ConfigMessages.getValue(line, vp);

		return line;
	}

	private void saveFile(YamlConfiguration config, File file) {
		try {
			config.save(file);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public String getHeader() {
		return "Die Liste aller Placeholder kannst du mit /varo ph aufrufen!";
	}

	@Override
	public void updateList() {
		this.scoreboardLines = new ArrayList<>();
		this.replaces = new HashMap<>();
		this.headers = new HashMap<>();

		File file = new File("plugins/Varo/config", "scoreboard.yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		ArrayList<String> scoreboard = new ArrayList<>();
		cfg.options().header(getHeader());
		cfg.options().copyDefaults(true);

		scoreboard.add("%space%");
		scoreboard.add("&7Team&8:");
		scoreboard.add("%colorcode%%team%");
		scoreboard.add("%space%");
		scoreboard.add("&7Kills&8:");
		scoreboard.add("%colorcode%%kills%");
		scoreboard.add("%space%");
		scoreboard.add("&7Zeit&8:");
		scoreboard.add("%colorcode%%min%&8:%colorcode%%sec%");
		scoreboard.add("%space%");
		scoreboard.add("&7Online: %colorcode%%online%");
		scoreboard.add("&7Remaining: %colorcode%%remaining%");
		scoreboard.add("&7Players: %colorcode%%players%");
		scoreboard.add("%space%");

		cfg.addDefault("header", "%projectname%");
		cfg.addDefault("scoreboard", scoreboard);

		if(cfg.contains("Scoreboard")) {
			scoreboardLines.addAll(cfg.getStringList("Scoreboard"));

			cfg.set("Scoreboard", null);
			cfg.set("scoreboard", scoreboardLines);
			saveFile(cfg, file);
		} else
			scoreboardLines.addAll(cfg.getStringList("scoreboard"));

		this.header = cfg.getString("header");
		
		if(!file.exists())
			saveFile(cfg, file);

		Collections.reverse(scoreboardLines);

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
		if(!ConfigSetting.SCOREBOARD.getValueAsBoolean() || !vp.getStats().isShowScoreboard())
			return;

		Player player = vp.getPlayer();
		Scoreboard sb = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		Objective obj = sb.registerNewObjective("silent", "dummy");
		
		headers.put(vp.getPlayer(), getConvString(header, vp));
		obj.setDisplayName(headers.get(vp.getPlayer()));
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		player.setScoreboard(sb);

		replaces.remove(player);
		updatePlayer(vp);
	}

	@Override
	public void updatePlayer(VaroPlayer player) {
		ArrayList<String> replacesLst = replaces.get(player.getPlayer());
		if(replacesLst == null)
			replaces.put(player.getPlayer(), replacesLst = new ArrayList<>());

		Scoreboard board = player.getPlayer().getScoreboard();
		Objective obj = board.getObjective(DisplaySlot.SIDEBAR);

		if(obj == null || !headers.containsKey(player.getPlayer())) {
			sendScoreBoard(player);
			return;
		}

		String header = getConvString(this.header, player);
		if(!headers.get(player.getPlayer()).equals(header))
			obj.setDisplayName(header);

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
}