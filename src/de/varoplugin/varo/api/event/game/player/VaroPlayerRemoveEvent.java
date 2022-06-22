package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;

public class VaroPlayerRemoveEvent extends VaroPlayerCancelableEvent {

    public VaroPlayerRemoveEvent(VaroPlayer player) {
        super(player);
    }
}