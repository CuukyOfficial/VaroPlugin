package de.varoplugin.varo.task;

public interface VaroTrigger extends Cloneable {

    void activate();

    void deactivate();

    boolean isActivated();

    void addChildren(VaroTrigger... children);

    void register(VaroRegistrable... register);

    /**
     * Deep clone.
     *
     * @return Deep cloned copy
     */
    VaroTrigger clone();
//    VaroTrigger deepClone();

}
