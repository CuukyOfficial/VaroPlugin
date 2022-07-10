package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.Player;
import de.varoplugin.varo.game.entity.player.ParticipantState;

public class VaroPlayerParticipantStateChangeEvent extends VaroPlayerCancelableEvent {

    private ParticipantState state;

    public VaroPlayerParticipantStateChangeEvent(Player player, ParticipantState state) {
        super(player);

        this.state = state;
    }

    public void setState(ParticipantState state) {
        this.state = state;
    }

    public ParticipantState getState() {
        return this.state;
    }
}