package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;

public class VaroPlayerAddEvent extends VaroPlayerCancelableEvent {

    public VaroPlayerAddEvent(VaroPlayer player) {
        super(player);
    }
}