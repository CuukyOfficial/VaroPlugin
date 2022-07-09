package de.varoplugin.varo.api.task.trigger.event;

import de.varoplugin.varo.api.task.trigger.Trigger;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TriggerDestroyEvent extends Event {

    private final Trigger trigger;

    private static final HandlerList handlers = new HandlerList();

    public TriggerDestroyEvent(Trigger trigger) {
        this.trigger = trigger;
    }

    public Trigger getTrigger() {
        return this.trigger;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
