package de.varoplugin.varo.api.event.game;

import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.game.Varo;

public class GameStateChangeEvent extends VaroGameCancelableEvent {

    private final VaroState state;

    public GameStateChangeEvent(Varo varo, VaroState state) {
        super(varo);
        this.state = state;
    }

    public VaroState getState() {
        return this.state;
    }
}