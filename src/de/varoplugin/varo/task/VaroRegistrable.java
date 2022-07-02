package de.varoplugin.varo.task;

import de.varoplugin.varo.game.Varo;

public interface VaroRegistrable extends Cloneable {

    void register();

    void deregister();

    boolean isRegistered();

    Varo getVaro();

    VaroRegistrable clone();

}
