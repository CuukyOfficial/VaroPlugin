package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.player.VaroParticipantState;

public class VaroPlayerParticipantStateChangeEvent extends VaroPlayerCancelableEvent {

    private VaroParticipantState state;

    public VaroPlayerParticipantStateChangeEvent(VaroPlayer player, VaroParticipantState state) {
        super(player);

        this.state = state;
    }

    public void setState(VaroParticipantState state) {
        this.state = state;
    }

    public VaroParticipantState getState() {
        return this.state;
    }
}