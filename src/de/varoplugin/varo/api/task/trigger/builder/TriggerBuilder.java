package de.varoplugin.varo.api.task.trigger.builder;

import de.varoplugin.varo.api.task.trigger.VaroTrigger;

public interface TriggerBuilder<B> {

    VaroTrigger build(B buildInfo);

    VaroTrigger complete(B buildInfo);

    TriggerBuilder<B> or(VaroTrigger trigger);

    TriggerBuilder<B> and(VaroTrigger... trigger);

}
