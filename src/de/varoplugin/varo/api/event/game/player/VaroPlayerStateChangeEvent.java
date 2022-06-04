package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.player.VaroPlayer;
import de.varoplugin.varo.game.player.state.VaroPlayerState;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class VaroPlayerStateChangeEvent extends VaroPlayerEvent {

    private final VaroPlayerState state;

    public VaroPlayerStateChangeEvent(Varo varo, VaroPlayer player, VaroPlayerState state) {
        super(varo, player);

        this.state = state;
    }

    public VaroPlayerState getState() {
        return this.state;
    }
}