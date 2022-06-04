package de.varoplugin.varo.game;

import de.varoplugin.varo.game.heartbeat.Heartbeat;

import java.util.Collection;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroState {

    Heartbeat createHeartbeat();

    Collection<CancelableListener> getListeners(Varo varo);

}