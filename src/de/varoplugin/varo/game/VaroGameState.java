package de.varoplugin.varo.game;

import de.varoplugin.varo.game.heartbeat.Heartbeat;

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
    LOBBY(null),
    RUNNING(null),
    MASS_RECORDING(null),
    FINISHED(null);

    private interface ListenerCreator extends Function<Varo, CancelableListener> {}

    private final Collection<ListenerCreator> DEFAULT_LISTENER = Collections.emptyList();

    private final Supplier<Heartbeat> heartbeatSupplier;
    private final Collection<ListenerCreator> listeners;

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