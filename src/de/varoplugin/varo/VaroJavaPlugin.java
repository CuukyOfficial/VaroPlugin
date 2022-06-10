package de.varoplugin.varo;

import de.varoplugin.varo.api.event.VaroEvent;
import de.varoplugin.varo.api.event.VaroLoadingStateChangeEvent;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.Game;
import de.varoplugin.varo.tasks.register.TaskRegister;
import de.varoplugin.varo.ui.UIManager;
import de.varoplugin.varo.ui.VaroUIManager;
import org.bukkit.event.Cancellable;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
@SuppressWarnings("unused")
public class VaroJavaPlugin extends JavaPlugin implements VaroPlugin {

	public static final String WEBSITE = "https://varoplugin.de/";
	public static final String GITHUB = "https://github.com/CuukyOfficial/VaroPlugin";
	public static final String DISCORD_INVITE = "https://discord.varoplugin.de/";

    private final VaroUIManager uiManager;
    private final Varo varo;
    private VaroLoadingState state;

    public VaroJavaPlugin() {
        this.varo = new Game();
        this.uiManager = new UIManager(this, varo);
    }

    /**
     * Thanks bukkit for removing all listeners before the plugin has been disabled
     */
    private void updateDisableState(VaroLoadingState state, Object... args) {
        this.uiManager.printDisableMessage(new VaroLoadingStateChangeEvent(state, state.formatMessage(args)));
        this.state = state;
    }

    private void updateLoadingState(VaroLoadingState state, Object... args) {
        this.callEvent(new VaroLoadingStateChangeEvent(state, state.formatMessage(args)));
        this.state = state;
    }

    private String getVersion() {
        return this.getDescription().getVersion();
    }

    @Override
    public void onEnable() {
        this.uiManager.registerUI();
        this.updateLoadingState(StartupState.INITIALIZING, this.getName(), this.getVersion());

        // Init

        this.updateLoadingState(StartupState.LOADING_STATS);

        // Load stats

        this.updateLoadingState(StartupState.REGISTERING_TASKS);
        this.getServer().getPluginManager().registerEvents(new TaskRegister(), this);
        this.varo.initialize(this);

        this.updateLoadingState(StartupState.FINISHED, this.getName());
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.updateDisableState(ShutdownState.INITIALIZING, this.getName(), this.getVersion());

        this.updateDisableState(ShutdownState.SAVING_STATS, 0);

        // Save stats

        this.updateDisableState(ShutdownState.SUCCESS);
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