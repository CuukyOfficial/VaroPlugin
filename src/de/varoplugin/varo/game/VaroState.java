package de.varoplugin.varo.game;

import de.varoplugin.varo.game.heartbeat.Heartbeat;
import de.varoplugin.varo.game.player.VaroPlayerState;

public interface VaroState {

    Heartbeat createHeartbeat();

    VaroPlayerState getDefaultPlayerState();

}