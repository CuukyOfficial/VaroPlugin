package de.varoplugin.varo.game.player;

import de.varoplugin.varo.game.Varo;

import java.util.UUID;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroPlayer {

    UUID getUuid();

    Varo getVaro();

    VaroPlayerState getState();

}