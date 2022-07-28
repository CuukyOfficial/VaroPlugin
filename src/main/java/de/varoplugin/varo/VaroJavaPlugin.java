/* 
 * VaroPlugin
 * Copyright (C) 2022 Cuuky, Almighty-Satan
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package de.varoplugin.varo;

import de.varoplugin.varo.api.event.VaroEvent;
import de.varoplugin.varo.bot.Bot;
import de.varoplugin.varo.bot.discord.DiscordBot;
import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.config.language.AbstractLanguage;
import de.varoplugin.varo.config.language.EnglishLanguage;
import de.varoplugin.varo.config.language.GermanLanguage;
import de.varoplugin.varo.config.language.Language;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.config.language.PlaceholderApiExpansion;
import de.varoplugin.varo.config.language.Placeholders;
import de.varoplugin.varo.config.language.placeholder.GlobalPlaceholder;
import de.varoplugin.varo.game.EmptyVaroBuilder;
import de.varoplugin.varo.game.State;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.task.register.DefaultTaskRegister;
import de.varoplugin.varo.ui.UIManager;
import de.varoplugin.varo.ui.VaroUIManager;

import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.plugin.java.JavaPlugin;

import com.j256.ormlite.support.ConnectionSource;

import java.io.File;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;

@SuppressWarnings("unused")
public class VaroJavaPlugin extends JavaPlugin implements VaroPlugin {

	public static final String WEBSITE = "https://varoplugin.de/";
	public static final String GITHUB = "https://github.com/CuukyOfficial/VaroPlugin";
	public static final String DISCORD_INVITE = "https://discord.varoplugin.de/";

	private static final String CONFIG_PATH = "config";
	private static final String LANGUAGE_PATH = "language";

	private VaroUIManager uiManager;
	private Varo varo;
	private VaroConfig config;
	private Language[] languages;
	private Messages messages;
	private ConnectionSource connectionSource;
	private final List<Bot> bots = new ArrayList<>();
	// TODO: External states
	private final Collection<State> states;

	public VaroJavaPlugin() {
		this.states = new HashSet<>();
		this.states.addAll(Arrays.asList(VaroState.values()));
	}

	private void updateLoadingState(VaroLoadingState state, Object... args) {
		this.uiManager.onLoadingStateUpdate(state, args);
	}

	@Override
	public void onEnable() {
		this.uiManager = new UIManager();
		this.uiManager.register(this);
		this.updateLoadingState(StartupState.INITIALIZING, this.getName(), this.getVersion());

		try {
			// Load Guava (this is only necessary on 1.7)
			// Dependencies.GUAVA.load(this);
			// Load config (This loads SnakeYAML and Simple-YAML)
			Dependencies.SIMPLE_YAML.load(this);
			Dependencies.SNAKE_YAML.load(this);
			this.config = new VaroConfig(this, this.getFile(), new File(this.getDataFolder(), CONFIG_PATH).getPath() + "/");
			this.config.load();
			// Load additional dependencies (e.g. JDA)
			Dependencies.ORMLITE_JDBC.load(this);
			Dependencies.loadNeeded(this);

			// Load Messages
			boolean placeholderApiSupport = this.config.placeholderapi.getValue() && Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
			Map<String, GlobalPlaceholder> globalPlaceholders = Placeholders.getPlaceholders(this);
			if (placeholderApiSupport)
				new PlaceholderApiExpansion(this.getName(), this.getVersion(), this.getAuthors(), globalPlaceholders).register();
			AbstractLanguage[] languages = new AbstractLanguage[] { new EnglishLanguage(0), new GermanLanguage(1) };
			int defaultLanguage = 0;
			for (AbstractLanguage language : languages) {
				if (this.config.defaultlanguage.getValue().equals(language.getName()))
					defaultLanguage = language.getId();
				language.load(new File(this.getDataFolder(), LANGUAGE_PATH + "/" + language.getName() + ".yml"));
			}
			this.languages = languages;
			this.messages = new Messages(languages, defaultLanguage, globalPlaceholders, placeholderApiSupport);
		} catch (Throwable t) {
			this.getLogger().log(Level.SEVERE, "Unable to load config", t);
			return;
		}

		this.updateLoadingState(StartupState.LOADING_STATS);

		// Load stats
		try {
			this.connectionSource = ConnectionSourceFactory.newConnectionSource(this.config);
		} catch (Throwable t) {
			this.getLogger().log(Level.SEVERE, "Unable to create jdbc connection source", t);
			return;
		}

		this.updateLoadingState(StartupState.REGISTERING_TASKS);
		this.getServer().getPluginManager().registerEvents(new DefaultTaskRegister(), this);

		this.varo = new EmptyVaroBuilder().states(this.states).create();
		this.varo.initialize(this);

		this.updateLoadingState(StartupState.STARTING_BOTS);
		// Load Discord bot
		if (this.config.bot_discord_enabled.getValue()) {
			Bot discordBot = new DiscordBot();
			discordBot.init(this);
			this.bots.add(discordBot);
		}

		this.updateLoadingState(StartupState.FINISHED, this.getName());
		super.onEnable();
	}

	@Override
	public void onDisable() {
		this.updateLoadingState(ShutdownState.INITIALIZING, this.getName(), this.getVersion());

		this.updateLoadingState(ShutdownState.STOPPING_BOTS, this.bots.size());
		this.bots.forEach(Bot::shutdown);

		this.updateLoadingState(ShutdownState.SAVING_STATS, this.varo.getPlayers().count());

		// Save stats

		this.updateLoadingState(ShutdownState.SUCCESS);
		super.onDisable();
	}

	@Override
	public <T extends VaroEvent> T callEvent(T event) {
		this.getServer().getPluginManager().callEvent(event);
		return event;
	}

	@Override
	public <T extends VaroEvent & Cancellable> boolean isCancelled(T event) {
		return this.callEvent(event).isCancelled();
	}

	@Override
	public File getFile() {
		return super.getFile();
	}

	@Override
	public VaroConfig getVaroConfig() {
		return this.config;
	}

	@Override
	public Language[] getLanguages() {
		return this.languages;
	}

	@Override
	public Messages getMessages() {
		return this.messages;
	}
	
	@Override
	public ConnectionSource getConnectionSource() {
		return this.connectionSource;
	}

	@Override
	public Varo getVaro() {
		return this.varo;
	}

	private String getVersion() {
		return this.getDescription().getVersion();
	}

	private String getAuthors() {
		return String.join(", ", this.getDescription().getAuthors());
	}

	@Override
	public String getWebsite() {
		return WEBSITE;
	}

	@Override
	public String getGithub() {
		return GITHUB;
	}

	@Override
	public String getDiscordInvite() {
		return DISCORD_INVITE;
	}
}