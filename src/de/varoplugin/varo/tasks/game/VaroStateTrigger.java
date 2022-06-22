package de.varoplugin.varo.tasks.game;

import de.varoplugin.varo.api.event.game.VaroStateChangeEvent;
import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.tasks.AbstractTaskTrigger;
import de.varoplugin.varo.tasks.VaroTask;
import de.varoplugin.varo.tasks.register.VaroRegisterInfo;
import org.bukkit.event.EventHandler;

/**
 * Triggers all tasks if the Varo is in the current state.
 */
public class VaroStateTrigger<T extends VaroRegisterInfo> extends AbstractTaskTrigger<T> {

    private final VaroState state;

    @SafeVarargs
    public VaroStateTrigger(VaroState state, VaroTask<T>... children) {
        super(children);

        this.state = state;
    }

    @Override
    public boolean shouldEnable() {
        return this.getInfo().getVaro().getState().equals(this.state);
    }

    @EventHandler
    public void onGameStateChange(VaroStateChangeEvent event) {
        if (!event.getState().equals(this.state)) this.unregisterTasks();
        else this.registerTasks();
    }
}