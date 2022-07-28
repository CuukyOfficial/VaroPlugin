package de.varoplugin.varo.game;

public interface State {

    boolean allowsNonRegistered();

    boolean allowsStart();

    boolean allowsPlayerMovement();

    boolean hasStartCountdown();

    boolean canFillChests();

    boolean canFinish();

    boolean canDoRandomTeam();

    boolean blocksProtectableAccess();

    boolean doesPlayerCountdown();

    /**
     * Defines which state will be executed if the game state cycles.
     *
     * @return The priority of the state
     */
    int getPriority();

}