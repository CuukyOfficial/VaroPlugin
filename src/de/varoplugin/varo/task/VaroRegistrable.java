package de.varoplugin.varo.task;

import de.varoplugin.varo.game.Varo;

public interface VaroRegistrable {

    void register(Varo varo);

    void deregister();

    void destroy();

    Varo getVaro();

}
