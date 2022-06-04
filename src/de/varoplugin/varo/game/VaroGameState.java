package de.varoplugin.varo.game;

import de.varoplugin.varo.game.heartbeat.Heartbeat;
import de.varoplugin.varo.game.heartbeat.RunningHeartbeat;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public enum VaroGameState implements VaroState {

    // TODO: Maybe as classes not enums for better expandability
    LOBBY,
    RUNNING(RunningHeartbeat::new),
    MASS_RECORDING,
    FINISHED;

    private interface ListenerCreator extends Function<Varo, CancelableListener> {}

    private final Collection<ListenerCreator> DEFAULT_LISTENER = Collections.emptyList();

    private final Supplier<Heartbeat> heartbeatSupplier;
    private final Collection<ListenerCreator> listeners;

    VaroGameState(ListenerCreator... listener) {
        this(null, listener);
    }

    VaroGameState(Supplier<Heartbeat> heartbeatSupplier, ListenerCreator... listener) {
        this.heartbeatSupplier = heartbeatSupplier;
        this.listeners = new LinkedList<>(this.DEFAULT_LISTENER);
        this.listeners.addAll(Arrays.asList(listener));
    }

    @Override
    public Collection<CancelableListener> getListeners(Varo varo) {
        return this.listeners.stream().map(l -> l.apply(varo)).collect(Collectors.toList());
    }

    @Override
    public Heartbeat createHeartbeat() {
        return this.heartbeatSupplier == null ? null : this.heartbeatSupplier.get();
    }

}