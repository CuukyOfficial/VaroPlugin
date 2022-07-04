package de.varoplugin.varo.api.task;

import de.varoplugin.varo.game.Varo;

public interface VaroTask extends Cloneable {

    void enable();

    void disable();

    boolean isEnabled();

    Varo getVaro();

    VaroTask clone();

}
