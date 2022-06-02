package de.varoplugin.varo;

import de.varoplugin.varo.event.VaroEvent;
import de.varoplugin.varo.event.VaroLoadingStateChangeEvent;
import de.varoplugin.varo.game.VaroGame;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.ui.UIManager;
import de.varoplugin.varo.ui.VaroUIManager;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class VaroLauncher extends JavaPlugin implements VaroPlugin {

    private final VaroUIManager uiManager;
    private final Varo varo;
    private VaroLoadingState state;

    public VaroLauncher() {
        this.uiManager = new UIManager(this);
        this.varo = new VaroGame();
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
        this.uiManager.registerListener();
        this.updateLoadingState(DefaultLoadingState.INITIALIZING, this.getName(), this.getVersion());
        this.varo.initialize(this);

        // Init

        this.updateLoadingState(DefaultLoadingState.LOADING_STATS);

        // Load stats

        this.updateLoadingState(DefaultLoadingState.FINISHED, this.getName());
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public <T extends VaroEvent> T callEvent(T event) {
        this.getServer().getPluginManager().callEvent(event);
        return event;
    }
}