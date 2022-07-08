package de.varoplugin.varo.api.task.trigger.builder;

import de.varoplugin.varo.api.task.trigger.VaroTrigger;

public interface TriggerBuilder {

    VaroTrigger build();

    VaroTrigger complete();

    TriggerBuilder when(VaroTrigger when);

    TriggerBuilder when(TriggerBuilder when);

    TriggerBuilder and(VaroTrigger and);

    TriggerBuilder and(TriggerBuilder and);

    TriggerBuilder down();

    TriggerBuilder ground();

}
