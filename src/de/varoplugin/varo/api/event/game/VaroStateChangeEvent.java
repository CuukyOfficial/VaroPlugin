package de.varoplugin.varo.api.event.game;

import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.game.Varo;

public class VaroStateChangeEvent extends VaroGameCancelableEvent {

    private final VaroState state;

    public VaroStateChangeEvent(Varo varo, VaroState state) {
        super(varo);
        this.state = state;
    }

    public VaroState getState() {
        return this.state;
    }
}