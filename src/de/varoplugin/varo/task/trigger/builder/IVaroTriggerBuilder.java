package de.varoplugin.varo.task.trigger.builder;

import de.varoplugin.varo.api.task.trigger.Trigger;
import de.varoplugin.varo.api.task.trigger.TriggerBuilder;
import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.game.State;

import java.util.function.Predicate;

public interface IVaroTriggerBuilder extends TriggerBuilder {

    IVaroTriggerBuilder when(Predicate<State> allowed);

    IVaroTriggerBuilder and(VaroConfig.VaroBoolConfigEntry entry);

    @Override
    Trigger build();

    @Override
    Trigger complete();

    @Override
    IVaroTriggerBuilder when(Trigger trigger);

    @Override
    TriggerBuilder when(TriggerBuilder when);

    @Override
    IVaroTriggerBuilder and(Trigger triggers);

    @Override
    IVaroTriggerBuilder and(TriggerBuilder and);

    @Override
    IVaroTriggerBuilder down();

    @Override
    IVaroTriggerBuilder ground();
}
