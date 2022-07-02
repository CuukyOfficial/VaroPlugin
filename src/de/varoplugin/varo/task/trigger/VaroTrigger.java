package de.varoplugin.varo.task.trigger;

import de.varoplugin.varo.task.VaroTask;

public interface VaroTrigger extends Cloneable {

    void activate();

    void deactivate();

    boolean isActivated();

    void addChildren(VaroTrigger... children);

    void register(VaroTask... tasks);

    VaroTrigger clone();

}
