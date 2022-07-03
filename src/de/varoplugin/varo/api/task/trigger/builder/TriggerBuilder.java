package de.varoplugin.varo.api.task.trigger.builder;

import de.varoplugin.varo.api.task.trigger.VaroTrigger;
import de.varoplugin.varo.game.task.trigger.builder.VaroTriggerBuilder;

public interface TriggerBuilder {

    VaroTrigger build();

    VaroTrigger complete();

    TriggerBuilder when(VaroTrigger when);

    TriggerBuilder when(VaroTriggerBuilder when);

    TriggerBuilder and(VaroTrigger... and);

    TriggerBuilder and(VaroTriggerBuilder and);

}
