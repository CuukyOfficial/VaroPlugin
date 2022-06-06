package de.varoplugin.varo.game.tasks;

import de.varoplugin.varo.api.event.game.VaroStateChangeEvent;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.VaroState;
import org.bukkit.event.EventHandler;

/**
 * Represents a Varo task, which depends on the current state of the Varo.
 * Unregisters itself if the state changes.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class VaroStateTask extends VaroTask {

    private final VaroState state;

    public VaroStateTask(Varo varo) {
        super(varo);

        this.state = varo.getState();
    }

    /**
     * Returns if the game state has changed.
     *
     * @param event The event
     * @return If the game state has changed
     */
    @EventHandler
    public boolean onGameStateChange(VaroStateChangeEvent event) {
        if (!this.state.equals(event.getState())) {
            this.unregister();
            this.varo.registerTasks(event.getState());
            return true;
        }
        return false;
    }
}