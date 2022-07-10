package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.Player;
import de.varoplugin.varo.game.entity.player.ParticipantState;

public class PlayerParticipantStateChangeEvent extends PlayerCancelableEvent {

    private ParticipantState state;

    public PlayerParticipantStateChangeEvent(Player player, ParticipantState state) {
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