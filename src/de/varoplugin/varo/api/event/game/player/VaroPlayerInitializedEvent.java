package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.Player;

public class VaroPlayerInitializedEvent extends VaroPlayerEvent {

    public VaroPlayerInitializedEvent(Player player) {
        super(player);
    }
}