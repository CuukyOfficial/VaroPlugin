package de.varoplugin.varo.event.game;

import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.event.VaroCancelableEvent;
import de.varoplugin.varo.game.Varo;

public class GameStateChangeEvent extends VaroCancelableEvent {

    private final VaroState newState;

    public GameStateChangeEvent(Varo varo, VaroState newState) {
        super(varo);
        this.newState = newState;
    }

    public VaroState getNewState() {
        return this.newState;
    }
}