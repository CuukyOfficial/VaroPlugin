package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.Player;
import de.varoplugin.varo.game.entity.player.PlayerMode;

public class PlayerModeChangeEvent extends PlayerCancelableEvent {

    private PlayerMode mode;

    public PlayerModeChangeEvent(Player player, PlayerMode mode) {
        super(player);

        this.mode = mode;
    }

    public void setMode(PlayerMode mode) {
        this.mode = mode;
    }

    public PlayerMode getMode() {
        return this.mode;
    }
}