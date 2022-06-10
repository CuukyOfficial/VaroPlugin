package de.varoplugin.varo;

import de.varoplugin.varo.api.event.VaroEvent;
import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.game.Game;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.tasks.register.TaskRegister;
import de.varoplugin.varo.ui.UIManager;
import de.varoplugin.varo.ui.VaroUIManager;
import org.bukkit.event.Cancellable;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
@SuppressWarnings("unused")
public class VaroJavaPlugin extends JavaPlugin implements VaroPlugin {

	public static final String WEBSITE = "https://varoplugin.de/";
	public static final String GITHUB = "https://github.com/CuukyOfficial/VaroPlugin";
	public static final String DISCORD_INVITE = "https://discord.varoplugin.de/";

    private static final String CONFIG_PATH = "config";

    private VaroUIManager uiManager;
    private Varo varo;
    private VaroConfig config;

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

        this.config = new VaroConfig(new File(this.getDataFolder(), CONFIG_PATH).getPath());

        this.updateLoadingState(StartupState.LOADING_STATS);

        // Load stats

        this.updateLoadingState(StartupState.REGISTERING_TASKS);

        this.getServer().getPluginManager().registerEvents(new TaskRegister(), this);
        this.varo = new Game();
        this.varo.initialize(this);

        this.updateLoadingState(StartupState.FINISHED, this.getName());
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.updateLoadingState(ShutdownState.INITIALIZING, this.getName(), this.getVersion());

        this.updateLoadingState(ShutdownState.SAVING_STATS, 0);

        // Save stats

        this.updateLoadingState(ShutdownState.SUCCESS);
        super.onDisable();
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
    public <T extends VaroEvent> T callEvent(T event) {
        this.getServer().getPluginManager().callEvent(event);
        return event;
    }

    @Override
    public <T extends VaroEvent & Cancellable> boolean isCancelled(T event) {
        return this.callEvent(event).isCancelled();
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