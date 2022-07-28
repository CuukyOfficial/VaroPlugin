package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;

public class PlayerInitializedEvent extends PlayerEvent {

    public PlayerInitializedEvent(VaroPlayer player) {
        super(player);
    }
}