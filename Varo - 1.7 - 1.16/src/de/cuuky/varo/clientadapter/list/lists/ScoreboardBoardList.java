package de.cuuky.varo.clientadapter.list.lists;

import java.util.ArrayList;

import de.cuuky.varo.clientadapter.list.BoardList;

public class ScoreboardBoardList extends BoardList {

	private String header;
	private ArrayList<String> scoreboardLines;

	public ScoreboardBoardList() {
		super("plugins/Varo/config/scoreboard.yml");
	}

	@Override
	protected void updateList() {
		this.scoreboardLines = new ArrayList<>();
		ArrayList<String> scoreboard = new ArrayList<>();
		configuration.options().header("Die Liste alle Placeholder findest du unter /varo placeholder!");

		scoreboard.add("%space%");
		scoreboard.add("&7Team&8:");
		scoreboard.add("%colorcode%%team%");
		scoreboard.add("%space%");
		scoreboard.add("&7Kills&8:");
		scoreboard.add("%colorcode%%kills%");
		scoreboard.add("%space%");
		scoreboard.add("&7Time&8:");
		scoreboard.add("%colorcode%%min%&8:%colorcode%%sec%");
		scoreboard.add("%space%");
		scoreboard.add("&7Online: %colorcode%%online%");
		scoreboard.add("&7Alive: %colorcode%%remaining%");
		scoreboard.add("&7Players: %colorcode%%players%");
		scoreboard.add("%space%");

		configuration.addDefault("header", "%projectname%");
		configuration.addDefault("scoreboard", scoreboard);

		scoreboardLines.addAll(configuration.getStringList("scoreboard"));

		this.header = configuration.getString("header");

		String space = "";
		for (int i = 0; i < scoreboardLines.size(); i++) {
			String line = scoreboardLines.get(i);
			if (line.equals("%space%")) {
				space += " ";
				scoreboardLines.set(i, space);
			}
		}
	}

	public String getHeader() {
		return this.header;
	}

	public ArrayList<String> getScoreboardLines() {
		return this.scoreboardLines;
	}
}