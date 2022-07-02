package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.api.event.game.VaroGameEvent;
import de.varoplugin.varo.game.entity.player.VaroPlayer;

public abstract class VaroPlayerEvent extends VaroGameEvent {

    private final VaroPlayer player;

    public VaroPlayerEvent(VaroPlayer player) {
        super(player.getVaro());

        this.player = player;
    }

    public VaroPlayer getPlayer() {
        return this.player;
    }
}