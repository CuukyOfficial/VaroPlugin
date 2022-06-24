package de.varoplugin.varo.jobs.game;

import de.varoplugin.varo.api.event.game.VaroStateChangeEvent;
import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.jobs.AbstractTaskTrigger;
import de.varoplugin.varo.jobs.VaroJob;
import org.bukkit.event.EventHandler;

/**
 * Triggers all tasks if the Varo is in the current state.
 */
public class VaroStateTrigger extends AbstractTaskTrigger {

    private final VaroState state;

    public VaroStateTrigger(VaroState state, VaroJob... children) {
        super(children);

        this.state = state;
    }

    @Override
    public boolean shouldEnable() {
        return this.getVaro().getState().equals(this.state);
    }

    @EventHandler
    public void onGameStateChange(VaroStateChangeEvent event) {
        if (!event.getState().equals(this.state)) this.unregisterJobs();
        else this.registerJobs();
    }
}