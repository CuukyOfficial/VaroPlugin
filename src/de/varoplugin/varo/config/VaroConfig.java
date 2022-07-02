package de.varoplugin.varo.config;

import com.google.common.collect.Multimap;
import de.varoplugin.varo.Dependencies;
import de.varoplugin.varo.VaroJavaPlugin;
import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.api.config.Config;
import de.varoplugin.varo.api.config.ConfigCategory;
import de.varoplugin.varo.api.config.ConfigEntry;
import de.varoplugin.varo.api.config.ConfigException;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static de.varoplugin.varo.config.VaroConfigCategory.*;

public class VaroConfig implements Config {

	private final List<ConfigEntry<?>> configEntries = new ArrayList<>();

	// These fields do not follow java conventions to improve readability
	public final VaroBoolConfigEntry offlinemode = new VaroBoolConfigEntry(MAIN, "offlinemode", false, "Whether the server is running in offline mode");

	public final VaroBoolConfigEntry bot_discord_enabled = new VaroBoolConfigEntry(BOTS, "bot.discord.enabled", false, "Whether the Discord Bot should be enabled");
	public final ConfigEntry<String> bot_discord_token = new VaroConfigEntry<>(BOTS, "bot.discord.token", "INSERT BOT TOKEN HERE", "The Bot Token");
	public final ConfigEntry<Long> bot_discord_guild = new VaroConfigEntry<>(BOTS, "bot.discord.guild", -1L, "TODO");
	public final ConfigEntry<String> bot_discord_invite = new VaroConfigEntry<>(BOTS, "bot.discord.invite", VaroJavaPlugin.DISCORD_INVITE, "TODO");
	public final ConfigEntry<String> bot_discord_status = new VaroConfigEntry<>(BOTS, "bot.discord.status", VaroJavaPlugin.WEBSITE, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_alert = new VaroConfigEntry<>(BOTS, "bot.discord.channels.alert", -1L, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_border = new VaroConfigEntry<>(BOTS, "bot.discord.channels.border", -1L, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_death = new VaroConfigEntry<>(BOTS, "bot.discord.channels.death", -1L, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_join = new VaroConfigEntry<>(BOTS, "bot.discord.channels.join", -1L, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_leave = new VaroConfigEntry<>(BOTS, "bot.discord.channels.leave", -1L, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_kill = new VaroConfigEntry<>(BOTS, "bot.discord.channels.kill", -1L, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_strike = new VaroConfigEntry<>(BOTS, "bot.discord.channels.strike", -1L, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_win = new VaroConfigEntry<>(BOTS, "bot.discord.channels.win", -1L, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_youtube = new VaroConfigEntry<>(BOTS, "bot.discord.channels.youtube", -1L, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_result = new VaroConfigEntry<>(BOTS, "bot.discord.channels.result", -1L, "TODO");
	public final VaroBoolConfigEntry bot_discord_embed_enabled = new VaroBoolConfigEntry(BOTS, "bot.discord.embed.enabled", true, "TODO");
	public final VaroBoolConfigEntry bot_discord_embed_randomcolor = new VaroBoolConfigEntry(BOTS, "bot.discord.embed.randomcolor", true, "TODO");
	public final ConfigEntry<String> bot_discord_modal_verify_title = new VaroConfigEntry<>(BOTS, "bot.discord.modal.verify.title", "Verify", "TODO");
	public final ConfigEntry<String> bot_discord_modal_verify_input_label = new VaroConfigEntry<>(BOTS, "bot.discord.modal.verify.inputlabel", "Code:", "TODO");
	public final VaroBoolConfigEntry bot_discord_command_verify_enabled = new VaroBoolConfigEntry(BOTS, "bot.discord.command.verify.enabled", true, "TODO");
	public final ConfigEntry<String> bot_discord_command_verify_name = new VaroConfigEntry<>(BOTS, "bot.discord.command.verify.name", "verify", "TODO");
	public final ConfigEntry<String> bot_discord_command_verify_desc = new VaroConfigEntry<>(BOTS, "bot.discord.command.verify.desc", "TODO", "TODO");
	public final VaroBoolConfigEntry bot_discord_command_status_enabled = new VaroBoolConfigEntry(BOTS, "bot.discord.command.status.enabled", true, "TODO");
	public final ConfigEntry<String> bot_discord_command_status_name = new VaroConfigEntry<>(BOTS, "bot.discord.command.status.name", "status", "TODO");
	public final ConfigEntry<String> bot_discord_command_status_desc = new VaroConfigEntry<>(BOTS, "bot.discord.command.status.desc", "TODO", "TODO");
	public final ConfigEntry<String> bot_discord_command_status_message = new VaroConfigEntry<>(BOTS, "bot.discord.command.status.message", "Whitelist: %whitelist%\nGame-State: %gamestate%\nOnline: %online%", "The message that is sent when a user executes /status");

	public final VaroBoolConfigEntry scoreboard_enabled = new VaroBoolConfigEntry(SCOREBOARD, "scoreboard.enabled", true, "Whether the scoreboard should be enabled (Players may still be able to hide their scoreboard)");
	public final ConfigEntry<Integer> scoreboard_title_delay = new VaroConfigEntry<>(SCOREBOARD, "scoreboard.title.updatedelay", 100, "The update interval of the title animation");
	public final ConfigEntry<Integer> scoreboard_content_delay = new VaroConfigEntry<>(SCOREBOARD, "scoreboard.content.updatedelay", 100, "The update interval of the animation");

	private Config config;

	/**
	 * This constructor is just for testing
	 * 
	 * @param path
	 */
	public VaroConfig(String path) {
		this.config = new ConfigImpl(path);

		for(ConfigEntry<?> entry : this.configEntries)
			this.addConfigEntry(entry);
	}

	public VaroConfig(VaroPlugin plugin, File pluginFile, String path) {
		try {
			// Download dependencies
			Dependencies.SIMPLE_YAML.load(plugin);
			Dependencies.SNAKE_YAML.load(plugin);

			// All classes except for ConfigImpl and SnakeYAML should still be loaded via the class loader of this class (Bukkit's ClassLoader)
			ClassLoader parentClassLoader = new ClassLoader(this.getClass().getClassLoader()) {

				@Override
				protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
					if (name.equals("de.varoplugin.varo.config.ConfigImpl") || name.startsWith("org.yaml.snakeyaml."))
						throw new ClassNotFoundException();
					else
						return super.loadClass(name, resolve);
				}
			};

			// Create URLClassLoader and load the ConfigImpl class using the newly created URLClassLoader
			URLClassLoader classLoader = new URLClassLoader(new URL[] {Dependencies.SIMPLE_YAML.getUrl(), Dependencies.SNAKE_YAML.getUrl(), pluginFile.toURI().toURL()}, parentClassLoader);
			Class<?> configClass = Class.forName("de.varoplugin.varo.config.ConfigImpl", false, classLoader);

			// Create new ConfigImpl instance
			this.config = (Config) configClass.getConstructor(String.class).newInstance(path);
		} catch (Throwable t) {
			plugin.getLogger().log(Level.SEVERE, "Unable to load config", t);
			return;
		}

		for(ConfigEntry<?> entry : this.configEntries)
			this.addConfigEntry(entry);
	}

	@Override
	public void load() throws ConfigException {
		this.config.load();
	}

	@Override
	public void dump() throws ConfigException {
		this.config.dump();
	}

	@Override
	public void delete() throws ConfigException {
		this.config.delete();
	}

	@Override
	public void addConfigEntry(ConfigEntry<?> configEntry) {
		this.config.addConfigEntry(configEntry);
	}

	@Override
	public Multimap<ConfigCategory, ConfigEntry<?>> getConfigEntries() {
		return this.config.getConfigEntries();
	}

	@Override
	public ConfigEntry<?> getEntry(ConfigCategory category, String path) {
		return this.config.getEntry(category, path);
	}

	private class VaroConfigEntry<T> extends ConfigEntryImpl<T> {

		private VaroConfigEntry(ConfigCategory category, String path, T defaultValue, String description) {
			super(category, path, defaultValue, description);

			VaroConfig.this.configEntries.add(this);
		}
	}

	public class VaroBoolConfigEntry extends VaroConfigEntry<Boolean> {
		
		private VaroBoolConfigEntry(ConfigCategory category, String path, boolean defaultValue, String description) {
			super(category, path, defaultValue, description);
		}

		public void ifTrue(Runnable runnable) {
			if (this.getValue())
				runnable.run();
		}
	}
}
