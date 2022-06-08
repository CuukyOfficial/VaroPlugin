package de.varoplugin.varo.game.tasks;

import de.varoplugin.varo.api.event.game.VaroStateChangeEvent;
import de.varoplugin.varo.game.VaroState;
import org.bukkit.event.EventHandler;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class VaroStateTrigger extends TaskTrigger {

    private final VaroState state;

    public VaroStateTrigger(VaroState state, TaskRegistrable... tasks) {
        super(tasks);

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