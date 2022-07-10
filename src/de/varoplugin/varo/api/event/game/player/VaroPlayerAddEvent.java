package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.Player;
import de.varoplugin.varo.game.entity.player.PlayerContainer;

public class VaroPlayerAddEvent extends VaroPlayerContainerEvent {

    public VaroPlayerAddEvent(PlayerContainer container, Player player) {
        super(container, player);
    }
}