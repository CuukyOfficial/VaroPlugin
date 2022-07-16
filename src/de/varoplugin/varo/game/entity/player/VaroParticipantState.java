package de.varoplugin.varo.game.entity.player;

/**
 * Contains all default participant states.
 */
public enum VaroParticipantState implements ParticipantState {

    NONE(false),
    ALIVE(true),
    DEAD(false);

    private final boolean sessions;

    VaroParticipantState(boolean sessions) {
        this.sessions = sessions;
    }

    @Override
    public boolean allowsSessions() {
        return this.sessions;
    }
}