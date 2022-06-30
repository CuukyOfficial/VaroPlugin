package de.varoplugin.varo.task.factory;

import de.varoplugin.varo.game.VaroState;

public interface VaroTriggerBuilder extends TriggerFactory {

    VaroTriggerBuilder or(VaroState state);

    VaroTriggerBuilder not(VaroState state);

    @Override
    VaroTriggerBuilder and(TriggerFactory trigger);

}
