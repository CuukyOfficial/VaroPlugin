package de.varoplugin.varo.game.player;

import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.game.player.info.AliveEndInfo;
import de.varoplugin.varo.game.player.info.AliveLobbyInfo;
import de.varoplugin.varo.game.player.info.AliveRunningInfo;
import de.varoplugin.varo.game.player.info.PlayerInfo;

import java.util.Arrays;
import java.util.HashSet;
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
    public PlayerInfo getInfo(VaroState state) {
        return this.infos.stream().filter(info -> info.getState().equals(state))
            .findAny().orElse(null);
    }

    @Override
    public boolean addInfo(PlayerInfo playerInfo) {
        return this.infos.add(playerInfo);
    }
}