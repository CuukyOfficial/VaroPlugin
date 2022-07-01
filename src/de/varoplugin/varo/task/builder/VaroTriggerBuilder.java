package de.varoplugin.varo.task.builder;

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.task.VaroTrigger;

public interface VaroTriggerBuilder extends TriggerBuilder<Varo> {

    VaroTriggerBuilder or(VaroState state);

    VaroTriggerBuilder orNot(VaroState state);

    @Override
    VaroTriggerBuilder and(VaroTrigger... trigger);

    @Override
    VaroTriggerBuilder or(VaroTrigger trigger);
}
