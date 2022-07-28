package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.player.PlayerContainer;

public class PlayerRemoveEvent extends PlayerContainerEvent {

    public PlayerRemoveEvent(PlayerContainer container, VaroPlayer player) {
        super(container, player);
    }
}