package de.varoplugin.varo.task.trigger.builder;

import de.varoplugin.varo.task.trigger.VaroTrigger;

public interface TriggerBuilder<B> {

    VaroTrigger build(B buildInfo);

    VaroTrigger complete(B buildInfo);

    TriggerBuilder<B> or(VaroTrigger trigger);

    TriggerBuilder<B> and(VaroTrigger... trigger);

}
