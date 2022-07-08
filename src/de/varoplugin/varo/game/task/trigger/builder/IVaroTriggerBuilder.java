package de.varoplugin.varo.game.task.trigger.builder;

import de.varoplugin.varo.api.task.trigger.VaroTrigger;
import de.varoplugin.varo.api.task.trigger.builder.TriggerBuilder;
import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.game.VaroState;

public interface IVaroTriggerBuilder extends TriggerBuilder {

    IVaroTriggerBuilder when(VaroState state);

    // TODO: Remove?
    IVaroTriggerBuilder whenNot(VaroState state);

    @Override
    VaroTrigger build();

    @Override
    VaroTrigger complete();

    @Override
    IVaroTriggerBuilder when(VaroTrigger trigger);

    @Override
    TriggerBuilder when(TriggerBuilder when);

    IVaroTriggerBuilder and(VaroConfig.VaroBoolConfigEntry entry);

    @Override
    IVaroTriggerBuilder and(VaroTrigger triggers);

    @Override
    IVaroTriggerBuilder and(TriggerBuilder and);

    @Override
    IVaroTriggerBuilder down();

    @Override
    IVaroTriggerBuilder ground();
}
