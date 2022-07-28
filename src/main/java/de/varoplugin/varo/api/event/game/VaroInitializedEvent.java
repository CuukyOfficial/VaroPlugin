package de.varoplugin.varo.api.event.game;

import de.varoplugin.varo.game.Varo;

public class VaroInitializedEvent extends VaroGameEvent {

    public VaroInitializedEvent(Varo varo) {
        super(varo);
    }
}