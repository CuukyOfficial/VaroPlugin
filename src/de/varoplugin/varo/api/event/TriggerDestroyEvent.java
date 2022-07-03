package de.varoplugin.varo.api.event;

import de.varoplugin.varo.api.task.trigger.VaroTrigger;

public class TriggerDestroyEvent extends VaroEvent {

    private final VaroTrigger trigger;

    public TriggerDestroyEvent(VaroTrigger trigger) {
        this.trigger = trigger;
    }

    public VaroTrigger getTrigger() {
        return this.trigger;
    }
}
