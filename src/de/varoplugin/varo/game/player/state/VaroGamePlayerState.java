package de.varoplugin.varo.game.player.state;

import de.varoplugin.varo.game.CancelableListener;
import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.game.player.VaroPlayer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents any default player state.
 * Provides infos about how the player in any @{@link VaroState} behaves.
 */
public enum VaroGamePlayerState implements VaroPlayerState {

    ALIVE(new AliveRunningInfo(), new AliveLobbyInfo(), new AliveEndInfo()),

    // TODO: Add infos for following player states
    SPECTATOR,
    GAME_MASTER,
    DEAD;

    private final Set<PlayerInfo> infos;

    VaroGamePlayerState(PlayerInfo... infos) {
        this.infos = new HashSet<>(Arrays.asList(infos));
    }

    @Override
    public List<CancelableListener> getListener(VaroState state, VaroPlayer player) {
        PlayerInfo playerInfo = this.infos.stream().filter(info -> info.getState().equals(state))
            .findAny().orElse(null);
        if (playerInfo == null) throw new IllegalStateException("Could not find info for state " + state);
        return playerInfo.getListener(player);
    }

    @Override
    public boolean addInfo(PlayerInfo playerInfo) {
        return this.infos.add(playerInfo);
    }
}