package de.varoplugin.varo.task;

import de.varoplugin.varo.game.Varo;

public interface VaroTask extends Cloneable {

    void register();

    void deregister();

    boolean isRegistered();

    Varo getVaro();

    VaroTask clone();

}
