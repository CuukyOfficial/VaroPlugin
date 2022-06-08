package de.varoplugin.varo.game.entity.player;

import de.varoplugin.varo.game.VaroState;

/**
 * Represents any default player state.
 * Provides infos about how the player in any @{@link VaroState} behaves.
 */
public enum VaroGamePlayerState implements VaroPlayerState {

    ALIVE,
    SPECTATOR,
    GAME_MASTER,
    DEAD

}