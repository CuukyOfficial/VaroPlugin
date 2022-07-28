package de.varoplugin.varo.api.event.game;

import de.varoplugin.varo.game.State;
import de.varoplugin.varo.game.Varo;

public class VaroStateChangeEvent extends VaroCancelableEvent {

    private final State state;

    public VaroStateChangeEvent(Varo varo, State state) {
        super(varo);
        this.state = state;
    }

    public State getState() {
        return this.state;
    }
}