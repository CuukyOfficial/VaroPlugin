package de.varoplugin.varo.task;

import de.varoplugin.varo.game.Varo;

public interface VaroRegistrable {

    void register();

    void deregister();

    boolean isRegistered();

    Varo getVaro();

    VaroRegistrable deepClone();

}
