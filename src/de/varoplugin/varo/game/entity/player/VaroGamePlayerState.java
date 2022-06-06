package de.varoplugin.varo.game.entity.player;

import de.varoplugin.varo.game.VaroGameState;
import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.game.entity.player.task.provider.AliveEndTaskProvider;
import de.varoplugin.varo.game.entity.player.task.provider.AliveLobbyTaskProvider;
import de.varoplugin.varo.game.entity.player.task.provider.AliveRunningTaskProvider;
import de.varoplugin.varo.game.entity.player.task.provider.VaroPlayerStateTaskProvider;
import de.varoplugin.varo.util.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents any default player state.
 * Provides infos about how the player in any @{@link VaroState} behaves.
 */
public enum VaroGamePlayerState implements VaroPlayerState {

    ALIVE(new Pair<>(VaroGameState.RUNNING, new AliveRunningTaskProvider()),
        new Pair<>(VaroGameState.LOBBY, new AliveLobbyTaskProvider()),
        new Pair<>(VaroGameState.FINISHED, new AliveEndTaskProvider())),

    // TODO: Add infos for following player states
    SPECTATOR,
    GAME_MASTER,
    DEAD;

    private final Map<VaroState, VaroPlayerStateTaskProvider> infos;

    @SafeVarargs
    VaroGamePlayerState(Pair<VaroState, VaroPlayerStateTaskProvider>... infos) {
        this.infos = new HashMap<>();
        Arrays.stream(infos).forEach(info -> this.infos.put(info.getKey(), info.getValue()));
    }

    @Override
    public VaroPlayerStateTaskProvider getInfo(VaroState state) {
        return this.infos.getOrDefault(state, null);
    }

    @Override
    public boolean addInfo(VaroState state, VaroPlayerStateTaskProvider taskProvider) {
        return this.infos.put(state, taskProvider) == null;
    }
}