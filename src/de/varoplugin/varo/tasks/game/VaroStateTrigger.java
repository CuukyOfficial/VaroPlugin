package de.varoplugin.varo.tasks.game;

import de.varoplugin.varo.api.event.game.VaroStateChangeEvent;
import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.tasks.AbstractTaskTrigger;
import de.varoplugin.varo.tasks.register.VaroRegisterInfo;
import de.varoplugin.varo.tasks.VaroTaskTrigger;
import de.varoplugin.varo.tasks.VaroTriggerCheckType;
import org.bukkit.event.EventHandler;

/**
 * Triggers all tasks if the Varo is in the current state.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public class VaroStateTrigger<T extends VaroRegisterInfo> extends AbstractTaskTrigger<T> {

    public enum VaroStateCheck implements VaroTriggerCheckType {
        STATE_CHECK
    }

    private final VaroState state;

    @SafeVarargs
    public VaroStateTrigger(VaroState state, VaroTaskTrigger<T>... combine) {
        super(combine);

        this.state = state;
        this.addCheck(VaroStateCheck.STATE_CHECK, () -> this.getInfo().getVaro().getState().equals(state));
    }

    @EventHandler
    public void onGameStateChange(VaroStateChangeEvent event) {
        if (!this.state.equals(event.getState())) this.unregisterTasks();
        else this.registerTasksActivated(VaroStateCheck.STATE_CHECK);
    }
}