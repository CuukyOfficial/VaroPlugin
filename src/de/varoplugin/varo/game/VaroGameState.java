package de.varoplugin.varo.game;

import de.varoplugin.varo.game.heartbeat.Heartbeat;
import de.varoplugin.varo.game.heartbeat.LobbyHeartbeat;
import de.varoplugin.varo.game.player.DefaultPlayerState;
import de.varoplugin.varo.game.player.VaroPlayerState;

import java.util.function.Supplier;

public enum VaroGameState implements VaroState {

    LOBBY(LobbyHeartbeat::new, DefaultPlayerState.ALIVE),
    RUNNING(null, DefaultPlayerState.SPECTATOR),
    MASS_RECORDING(null, RUNNING.defaultState),
    FINISHED(null, RUNNING.defaultState);

    private final Supplier<Heartbeat> heartbeatSupplier;
    private final VaroPlayerState defaultState;

    VaroGameState(Supplier<Heartbeat> heartbeatSupplier, VaroPlayerState defaultState) {
        this.heartbeatSupplier = heartbeatSupplier;
        this.defaultState = defaultState;
    }

    @Override
    public Heartbeat createHeartbeat() {
        return this.heartbeatSupplier.get();
    }

    @Override
    public VaroPlayerState getDefaultPlayerState() {
        return this.defaultState;
    }
}