package de.cuuky.varo.configuration.configurations.config;

import java.util.ArrayList;

import de.cuuky.cfw.player.hud.AnimationData;
import de.cuuky.varo.Main;

public class ActionbarConfig extends BoardConfig {

	private AnimationData<String> content;

	public ActionbarConfig() {
		super("plugins/Varo/config/actionbar.yml");
	}

	@Override
	protected boolean shouldReset() {
		return false;
	}
	
	@Override
	protected void load() {
		this.configuration.options().header("Die Liste alle Placeholder findest du unter /varo placeholder!");
		
		ArrayList<String> titleFrames = new ArrayList<>();
		titleFrames.add("&7Distance to border: %colorcode%%distanceToBorder%");
		titleFrames.add("&7Check out our Discord %colorcode%" + Main.DISCORD_INVITE);

		this.configuration.addDefault("updatedelay", 100);
		this.configuration.addDefault("content", titleFrames);

		this.content = new AnimationData<>(this.configuration.getInt("updatedelay"), this.configuration.getStringList("content").toArray(new String[0]));
	}

	public AnimationData<String> getContent() {
		return this.content;
	}
}