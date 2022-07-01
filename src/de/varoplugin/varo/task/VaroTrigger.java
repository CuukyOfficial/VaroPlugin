package de.varoplugin.varo.task;

public interface VaroTrigger {

    void activate();

    void deactivate();

    boolean isActivated();

    void addChildren(VaroTrigger... children);

    void register(VaroRegistrable... register);

    VaroTrigger deepClone();

}
