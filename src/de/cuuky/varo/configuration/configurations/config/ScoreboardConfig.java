package de.cuuky.varo.configuration.configurations.config;

import java.util.ArrayList;
import java.util.List;

import de.varoplugin.cfw.player.hud.AnimationData;
import de.varoplugin.cfw.player.hud.UnmodifiableAnimationData;

public class ScoreboardConfig extends BoardConfig {

	private AnimationData<String> title;
	private AnimationData<String[]> scoreboard;

	public ScoreboardConfig() {
		super("plugins/Varo/config/scoreboard.yml");
	}

	@Override
	protected boolean shouldReset() {
		return this.configuration.contains("header");
	}
	
	@Override
	protected void load() {
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
		firstFrame.add("%colorcode%%hour%&8:%colorcode%%min%&8:%colorcode%%sec%");
		firstFrame.add("                   ");
		firstFrame.add("&7Online: %colorcode%%online%");
		firstFrame.add("&7Alive: %colorcode%%remaining%");
		firstFrame.add("&7Players: %colorcode%%players%");
		firstFrame.add("&7Strikes: %colorcode%%strikes%");
		firstFrame.add("%space%");

		ArrayList<String> secondFrame = new ArrayList<>();
		secondFrame.add("%space%");
		secondFrame.add("&7Team&8:");
		secondFrame.add("%colorcode%%team%");
		secondFrame.add("%space%");
		secondFrame.add("&7Kills&8:");
		secondFrame.add("%colorcode%%kills%");
		secondFrame.add("%space%");
		secondFrame.add("&7Time&8:");
		secondFrame.add("%colorcode%%hour%&8:%colorcode%%min%&8:%colorcode%%sec%");
		secondFrame.add("                   ");
		secondFrame.add("&7Best Players&8:");
		secondFrame.add("&71. %colorcode%%topplayer-1%");
		secondFrame.add("&72. %colorcode%%topplayer-2%");
		secondFrame.add("&73. %colorcode%%topplayer-3%");
		secondFrame.add("%space%");

		ArrayList<ArrayList<String>> frames = new ArrayList<>();
		frames.add(firstFrame);
		frames.add(secondFrame);

		this.configuration.addDefault("title.updatedelay", 0);
		this.configuration.addDefault("title.content", titleFrames);
		this.configuration.addDefault("scoreboard.updatedelay", 100);
		this.configuration.addDefault("scoreboard.content", frames);

		this.title = new UnmodifiableAnimationData<>(this.configuration.getInt("title.updatedelay"), this.configuration.getStringList("title.content").toArray(new String[0]));
		this.scoreboard = toAnimationData(this.configuration.getInt("scoreboard.updatedelay"), this.configuration.getList("scoreboard.content"));
	}

	public AnimationData<String> getTitle() {
		return this.title;
	}

	public AnimationData<String[]> getScoreboard() {
		return this.scoreboard;
	}
	
	private static AnimationData<String[]> toAnimationData(int delay, List<?> frames) {
	    return new UnmodifiableAnimationData<>(delay, frames.stream().map(f -> {
	        String[] frameArray = ((List<?>) f).toArray(new String[0]);
	        
	        String space = "";
	        for (int i = 0; i < frameArray.length; i++)
	            if (frameArray[i].equals("%space%")) {
	                space += " ";
	                frameArray[i] = space;
	            }
	        return frameArray;
	    }).toArray(String[][]::new));
	}
}