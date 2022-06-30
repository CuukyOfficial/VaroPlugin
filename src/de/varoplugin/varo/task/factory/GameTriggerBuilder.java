package de.varoplugin.varo.task.factory;

import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.task.GameStateTrigger;
import de.varoplugin.varo.task.VaroTrigger;

import java.util.HashSet;
import java.util.Set;

public class GameTriggerBuilder implements VaroTriggerBuilder {

    private final Set<VaroTrigger> triggerSet;

    public GameTriggerBuilder() {
        this.triggerSet = new HashSet<>();
    }

    @Override
    public VaroTrigger build() {
        VaroTrigger wrapper = new GameTriggerWrapper();
        this.triggerSet.forEach(t -> t.addChildren(wrapper));
        return wrapper;
    }

    @Override
    public VaroTriggerBuilder or(VaroState state) {
        this.triggerSet.add(new GameStateTrigger(state));
        return this;
    }

    @Override
    public VaroTriggerBuilder not(VaroState state) {
        this.triggerSet.add(new GameStateTrigger(state, false));
        return this;
    }

    @Override
    public VaroTriggerBuilder and(TriggerFactory trigger) {
        this.triggerSet.add(trigger.build());
        return this;
    }
}
