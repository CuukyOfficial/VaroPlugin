package de.varoplugin.varo.game;

public enum VaroState implements State {

    LOBBY(100, true, true, false, false),
    STARTING(200, false, false, true, false),
    RUNNING(300, false, false, false, true),
    MASS_RECORDING(400, false, false, false, true),
    FINISHED(500, true, false, false, false);

    private final int priority;
    private final boolean allowsUnregistered;
    private final boolean lobby;
    private final boolean starting;
    private final boolean running;

    VaroState(int priority, boolean allowsUnregistered, boolean lobby, boolean starting, boolean running) {
        this.priority = priority;
        this.allowsUnregistered = allowsUnregistered;
        this.lobby = lobby;
        this.starting = starting;
        this.running = running;
    }

    @Override
    public boolean allowsNonRegistered() {
        return this.allowsUnregistered;
    }

    @Override
    public boolean allowsStart() {
        return this.lobby;
    }

    @Override
    public boolean allowsPlayerMovement() {
        return !this.lobby;
    }

    @Override
    public boolean hasStartCountdown() {
        return this.starting;
    }

    @Override
    public boolean canFillChests() {
        return this.running;
    }

    @Override
    public boolean canFinish() {
        return this.running;
    }

    @Override
    public boolean canDoRandomTeam() {
        return this.starting;
    }

    @Override
    public boolean blocksProtectableAccess() {
        return this.running;
    }

    @Override
    public boolean doesPlayerCountdown() {
        return this.running;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }
}