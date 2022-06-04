package de.varoplugin.varo.api.event;

import de.varoplugin.varo.VaroLoadingState;

public class VaroLoadingStateChangeEvent extends VaroEvent {

    private final VaroLoadingState state;
    private final String message;

    public VaroLoadingStateChangeEvent(VaroLoadingState state, String message) {
        this.state = state;
        this.message = message;
    }

    public VaroLoadingState getState() {
        return state;
    }

    public String getMessage() {
        return this.message;
    }
}