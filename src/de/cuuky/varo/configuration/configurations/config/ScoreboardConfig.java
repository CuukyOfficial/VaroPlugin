package de.cuuky.varo.configuration.configurations.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import de.cuuky.cfw.player.AnimationData;
import de.cuuky.cfw.player.ScoreboardAnimationData;

public class ScoreboardConfig extends BoardConfig {

	private AnimationData<String> title;
	private ScoreboardAnimationData scoreboard;

	public ScoreboardConfig() {
		super("plugins/Varo/config/scoreboard.yml");
	}

	@Override
	protected void load() {
		// Remove legacy config entries
		if (this.configuration.contains("header"))
			this.configuration = new YamlConfiguration();
		
		this.configuration.options().header("Die Liste alle Placeholder findest du unter /varo placeholder!");

		ArrayList<String> titleFrames = new ArrayList<>();
		titleFrames.add("%projectname%");

		ArrayList<String> firstFrame = new ArrayList<>();
		firstFrame.add("%space%");
		firstFrame.add("&7Team&8:");
		firstFrame.add("%colorcode%%team%");
		firstFrame.add("%space%");
		firstFrame.add("&7Kills&8:");
		firstFrame.add("%colorcode%%kills%");
		firstFrame.add("%space%");
		firstFrame.add("&7Time&8:");
		firstFrame.add("%colorcode%%min%&8:%colorcode%%sec%");
		firstFrame.add("%space%");
		firstFrame.add("&7Online: %colorcode%%online%");
		firstFrame.add("&7Alive: %colorcode%%remaining%");
		firstFrame.add("&7Players: %colorcode%%players%");
		firstFrame.add("&7Strikes: %colorcode%%strikes%");
		firstFrame.add("                   ");

		ArrayList<String> secondFrame = new ArrayList<>();
		secondFrame.add("%space%");
		secondFrame.add("&7Team&8:");
		secondFrame.add("%colorcode%%team%");
		secondFrame.add("%space%");
		secondFrame.add("&7Kills&8:");
		secondFrame.add("%colorcode%%kills%");
		secondFrame.add("%space%");
		secondFrame.add("&7Time&8:");
		secondFrame.add("%colorcode%%min%&8:%colorcode%%sec%");
		secondFrame.add("%space%");
		secondFrame.add("&7Best Teams&8:");
		secondFrame.add("&71. %colorcode%%topteam-1%");
		secondFrame.add("&72. %colorcode%%topteam-2%");
		secondFrame.add("&73. %colorcode%%topteam-3%");
		secondFrame.add("                   ");

		ArrayList<ArrayList<String>> frames = new ArrayList<>();
		frames.add(firstFrame);
		frames.add(secondFrame);

		this.configuration.addDefault("title.updatedelay", 0);
		this.configuration.addDefault("title.content", titleFrames);
		this.configuration.addDefault("scoreboard.updatedelay", 100);
		this.configuration.addDefault("scoreboard.content", frames);

		this.title = new AnimationData<>(this.configuration.getInt("title.updatedelay"), this.configuration.getStringList("title.content").toArray(new String[0]));
		@SuppressWarnings("unchecked")
		List<List<String>> configFrames = (List<List<String>>) this.configuration.getList("scoreboard.content");
		this.scoreboard = new ScoreboardAnimationData(this.configuration.getInt("scoreboard.updatedelay"), configFrames.stream().map(frame -> frame.toArray(new String[0])).toArray(String[][]::new));
	}

	public AnimationData<String> getTitle() {
		return title;
	}

	public ScoreboardAnimationData getScoreboard() {
		return scoreboard;
	}
}