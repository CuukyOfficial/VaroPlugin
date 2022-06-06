package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.player.VaroPlayerState;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class VaroPlayerStateChangeEvent extends VaroPlayerEvent {

    private final VaroPlayerState state;

    public VaroPlayerStateChangeEvent(VaroPlayer player, VaroPlayerState state) {
        super(player);

        this.state = state;
    }

    public VaroPlayerState getState() {
        return this.state;
    }
}