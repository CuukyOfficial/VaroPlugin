package de.varoplugin.varo.game;

import de.varoplugin.varo.game.heartbeat.Heartbeat;
import de.varoplugin.varo.game.player.VaroPlayerState;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroState {

    Heartbeat createHeartbeat();

    VaroPlayerState getPlayerState();

}