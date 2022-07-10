package de.varoplugin.varo.api.task.trigger;

public interface TriggerBuilder {

    Trigger build();

    Trigger complete();

    TriggerBuilder when(Trigger when);

    TriggerBuilder when(TriggerBuilder when);

    TriggerBuilder and(Trigger and);

    TriggerBuilder and(TriggerBuilder and);

    TriggerBuilder down();

    TriggerBuilder ground();

}
