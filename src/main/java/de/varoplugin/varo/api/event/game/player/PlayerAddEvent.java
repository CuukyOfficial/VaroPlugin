package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.player.PlayerContainer;

public class PlayerAddEvent extends PlayerContainerEvent {

    public PlayerAddEvent(PlayerContainer container, VaroPlayer player) {
        super(container, player);
    }
}