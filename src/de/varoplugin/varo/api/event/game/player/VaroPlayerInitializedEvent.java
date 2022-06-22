package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;

public class VaroPlayerInitializedEvent extends VaroPlayerEvent {

    public VaroPlayerInitializedEvent(VaroPlayer player) {
        super(player);
    }
}