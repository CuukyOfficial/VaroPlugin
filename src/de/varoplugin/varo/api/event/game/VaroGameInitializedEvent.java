package de.varoplugin.varo.api.event.game;

import de.varoplugin.varo.game.Varo;

public class VaroGameInitializedEvent extends VaroGameEvent {

    public VaroGameInitializedEvent(Varo varo) {
        super(varo);
    }
}