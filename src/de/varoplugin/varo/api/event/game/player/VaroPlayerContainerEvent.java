package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.Player;
import de.varoplugin.varo.game.entity.player.PlayerContainer;

public class VaroPlayerContainerEvent extends VaroPlayerCancelableEvent {

    private final PlayerContainer container;

    public VaroPlayerContainerEvent(PlayerContainer container, Player player) {
        super(player);

        this.container = container;
    }

    public PlayerContainer getContainer() {
        return this.container;
    }
}
