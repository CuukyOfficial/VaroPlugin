package de.varoplugin.varo.game;

public enum VaroState implements State {

    LOBBY(100, true),
    STARTING(200, true),
    RUNNING(300, false),
    MASS_RECORDING(400, false),
    FINISHED(500, true);

    private final int priority;
    private final boolean allowsUnregistered;

    VaroState(int priority, boolean allowsUnregistered) {
        this.priority = priority;
        this.allowsUnregistered = allowsUnregistered;
    }

    @Override
    public boolean allowsNonRegistered() {
        return this.allowsUnregistered;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }
}