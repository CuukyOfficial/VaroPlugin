package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.player.VaroPlayerContainer;

public class VaroPlayerAddEvent extends VaroPlayerContainerEvent {

    public VaroPlayerAddEvent(VaroPlayerContainer container, VaroPlayer player) {
        super(container, player);
    }
}