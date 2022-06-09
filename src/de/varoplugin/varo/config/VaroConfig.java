package de.varoplugin.varo.config;

import static de.varoplugin.varo.config.VaroConfigCategory.*;

import de.varoplugin.varo.api.config.ConfigEntry;

public class VaroConfig extends AbstractConfig {

	// These fields do not follow java conventions to improve readability
	public final ConfigEntry<Boolean> offlinemode = new ConfigEntryImpl<>(MAIN, "offlinemode", false, "Whether the server is running in offline mode");

	public final ConfigEntry<Boolean> scoreboard_enabled = new ConfigEntryImpl<>(SCOREBOARD, "scoreboard.enabled", true, "Whether the scoreboard should be enabled (Players may still be able to hide their scoreboard)");
	public final ConfigEntry<Integer> scoreboard_title_delay = new ConfigEntryImpl<>(SCOREBOARD, "scoreboard.title.updatedelay", 100, "The update interval of the title animation");
	public final ConfigEntry<Integer> scoreboard_content_delay = new ConfigEntryImpl<>(SCOREBOARD, "scoreboard.content.updatedelay", 100, "The update interval of the animation");

	public VaroConfig(String path) {
		super(path);
	}
}
