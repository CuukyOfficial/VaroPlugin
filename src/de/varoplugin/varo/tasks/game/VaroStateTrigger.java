package de.varoplugin.varo.tasks.game;

import de.varoplugin.varo.api.event.game.VaroStateChangeEvent;
import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.tasks.AbstractTaskTrigger;
import de.varoplugin.varo.tasks.VaroTask;
import org.bukkit.event.EventHandler;

/**
 * Triggers all tasks if the Varo is in the current state.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public class VaroStateTrigger<T extends VaroTask> extends AbstractTaskTrigger<T> {

    private final VaroState state;

    public VaroStateTrigger(VaroState state) {
        this.state = state;
        this.addCheck(VaroStateTrigger.class, () -> this.varo.getState().equals(state));
    }

    @EventHandler
    public void onGameStateChange(VaroStateChangeEvent event) {
        if (!this.state.equals(event.getState())) {
            this.unregisterTasks();
        } else if (this.isActivated(VaroStateTrigger.class))
            this.registerTasks();
    }
}