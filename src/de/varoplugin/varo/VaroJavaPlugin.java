package de.varoplugin.varo;

import de.varoplugin.varo.api.config.ConfigException;
import de.varoplugin.varo.api.event.VaroEvent;
import de.varoplugin.varo.bot.Bot;
import de.varoplugin.varo.bot.discord.DiscordBot;
import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.dependencies.Dependencies;
import de.varoplugin.varo.dependencies.InvalidSignatureException;
import de.varoplugin.varo.game.Game;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.jobs.register.JobRegister;
import de.varoplugin.varo.ui.UIManager;
import de.varoplugin.varo.ui.VaroUIManager;
import org.bukkit.event.Cancellable;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class VaroJavaPlugin extends JavaPlugin implements VaroPlugin {

	public static final String WEBSITE = "https://varoplugin.de/";
	public static final String GITHUB = "https://github.com/CuukyOfficial/VaroPlugin";
	public static final String DISCORD_INVITE = "https://discord.varoplugin.de/";

	private static final String CONFIG_PATH = "config";

	private VaroUIManager uiManager;
	private Varo varo;
	private VaroConfig config;
	private final List<Bot> bots = new ArrayList<>();

	private void updateLoadingState(VaroLoadingState state, Object... args) {
		this.uiManager.onLoadingStateUpdate(state, args);
	}

	private String getVersion() {
		return this.getDescription().getVersion();
	}

	@Override
	public void onEnable() {
		this.uiManager = new UIManager();
		this.uiManager.register(this);
		this.updateLoadingState(StartupState.INITIALIZING, this.getName(), this.getVersion());

		try {
			// Load Guava (this is only necessary on 1.7)
			Dependencies.GUAVA.load(this);
			// Load config (This loads SnakeYAML and Simple-YAML)
			this.config = new VaroConfig(this, this.getFile(), new File(this.getDataFolder(), CONFIG_PATH).getPath() + "/");
			this.config.load();
			// Load additional dependencies (e.g. JDA)
			Dependencies.loadNeeded(this);
		} catch (ConfigException | IOException | InvalidSignatureException e) {
			e.printStackTrace();
		}

		this.updateLoadingState(StartupState.LOADING_STATS);

		// Load stats

		this.updateLoadingState(StartupState.REGISTERING_TASKS);

		this.getServer().getPluginManager().registerEvents(new JobRegister(), this);
		this.varo = new Game();
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

        this.updateLoadingState(ShutdownState.SAVING_STATS, 0);

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
    public Varo getVaro() {
        return this.varo;
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