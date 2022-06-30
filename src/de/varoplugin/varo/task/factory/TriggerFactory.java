package de.varoplugin.varo.task.factory;

import de.varoplugin.varo.task.VaroTrigger;

public interface TriggerFactory {

    TriggerFactory and(TriggerFactory factory);

    VaroTrigger build();

}
