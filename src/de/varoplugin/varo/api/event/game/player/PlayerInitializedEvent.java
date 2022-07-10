package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.Player;

public class PlayerInitializedEvent extends PlayerEvent {

    public PlayerInitializedEvent(Player player) {
        super(player);
    }
}