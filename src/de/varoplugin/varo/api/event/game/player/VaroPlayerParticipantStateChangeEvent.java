package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.player.VaroParticipantState;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class VaroPlayerParticipantStateChangeEvent extends VaroPlayerCancelableEvent {

    private final VaroParticipantState state;

    public VaroPlayerParticipantStateChangeEvent(VaroPlayer player, VaroParticipantState state) {
        super(player);

        this.state = state;
    }

    public VaroParticipantState getState() {
        return this.state;
    }
}