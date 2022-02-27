package de.cuuky.varo;

import de.cuuky.varo.heartbeat.LobbyHeartbeat;
import de.cuuky.varo.heartbeat.RunningHeartbeat;
import de.cuuky.varo.heartbeat.UpdateHeartbeat;

import java.util.function.Function;

public enum VaroGameState implements GameState {

    LOBBY(LobbyHeartbeat::new, false),
    RUNNING(RunningHeartbeat::new, true),
    MASS_RECORDING(RunningHeartbeat::new, true),
    END(UpdateHeartbeat::new, false);

    private final Function<Varo, Heartbeat> heartbeat;
    private final boolean eventsAllowed;

    VaroGameState(Function<Varo, Heartbeat> heartbeat, boolean eventsAllowed) {
        this.heartbeat = heartbeat;
        this.eventsAllowed = eventsAllowed;
    }

    @Override
    public boolean eventsAllowed() {
        return this.eventsAllowed;
    }

    @Override
    public Heartbeat apply(Varo varo) {
        return this.heartbeat.apply(varo);
    }
}