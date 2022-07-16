package de.varoplugin.varo.game.entity.player;

/**
 * Contains all default player modes.
 */
public enum VaroPlayerMode implements PlayerMode {

    NONE(true),
    SPEC(false),
    GAME_MASTER(false);

    private final boolean countsSessions;

    VaroPlayerMode(boolean countsSessions) {
        this.countsSessions = countsSessions;
    }

    @Override
    public boolean countsSessions() {
        return this.countsSessions;
    }
}