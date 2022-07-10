package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.api.event.game.VaroGameEvent;
import de.varoplugin.varo.game.entity.player.Player;

public abstract class PlayerEvent extends VaroGameEvent {

    private final Player player;

    public PlayerEvent(Player player) {
        super(player.getVaro());

        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }
}