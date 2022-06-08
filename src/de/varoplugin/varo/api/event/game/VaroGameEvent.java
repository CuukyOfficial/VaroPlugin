package de.varoplugin.varo.api.event.game;

import de.varoplugin.varo.api.event.VaroEvent;
import de.varoplugin.varo.game.Varo;

public abstract class VaroGameEvent extends VaroEvent {

    private final Varo varo;

    public VaroGameEvent(Varo varo) {
        this.varo = varo;
    }

    public Varo getVaro() {
        return this.varo;
    }
}