package de.varoplugin.varo.api.task.trigger;

import de.varoplugin.varo.api.task.Task;
import org.bukkit.event.Listener;

public interface Trigger extends Cloneable, Listener {

    void activate();

    void deactivate();

    boolean isActivated();

    void addChildren(Trigger... children);

    void register(Task... tasks);

    void destroy();

    boolean wasDestroyed();

    Trigger clone();

}
