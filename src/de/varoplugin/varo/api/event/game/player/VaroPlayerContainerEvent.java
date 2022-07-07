package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.player.VaroPlayerContainer;

public class VaroPlayerContainerEvent extends VaroPlayerCancelableEvent {

    private final VaroPlayerContainer container;

    public VaroPlayerContainerEvent(VaroPlayerContainer container, VaroPlayer player) {
        super(player);

        this.container = container;
    }

    public VaroPlayerContainer getContainer() {
        return this.container;
    }
}
