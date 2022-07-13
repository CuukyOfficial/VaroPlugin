package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.Player;
import de.varoplugin.varo.game.entity.player.PlayerContainer;

public class PlayerAddEvent extends PlayerContainerEvent {

    public PlayerAddEvent(PlayerContainer container, Player player) {
        super(container, player);
    }
}