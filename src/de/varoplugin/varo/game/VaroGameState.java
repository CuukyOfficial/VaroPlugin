package de.varoplugin.varo.game;

import de.varoplugin.varo.game.tasks.LobbyLoginListener;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public enum VaroGameState implements VaroState {

    // TODO: Maybe as classes not enums for better expandability
    LOBBY(LobbyLoginListener::new),
    RUNNING,
    MASS_RECORDING,
    FINISHED;

    @FunctionalInterface
    private interface ListenerCreator extends Function<Varo, CancelableTask> {}

    private final Collection<ListenerCreator> listeners;

    VaroGameState(ListenerCreator... listener) {
        this.listeners = new LinkedList<>();
        this.listeners.addAll(Arrays.asList(listener));
    }

    @Override
    public Collection<CancelableTask> getTasks(Varo varo) {
        return this.listeners.stream().map(l -> l.apply(varo)).collect(Collectors.toList());
    }
}