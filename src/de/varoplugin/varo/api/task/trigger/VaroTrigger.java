package de.varoplugin.varo.api.task.trigger;

import de.varoplugin.varo.api.task.VaroTask;
import org.bukkit.event.Listener;

public interface VaroTrigger extends Cloneable, Listener {

    void activate();

    void deactivate();

    boolean isActivated();

    void addChildren(VaroTrigger... children);

    void register(VaroTask... tasks);

    void destroy();

    boolean wasDestroyed();

    VaroTrigger clone();

}
