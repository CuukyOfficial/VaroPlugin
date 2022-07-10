package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.Player;

public class PlayerCountdownChangeEvent extends PlayerCancelableEvent {

    private int countdown;

    public PlayerCountdownChangeEvent(Player player, int countdown) {
        super(player);
        this.countdown = countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    public int getCountdown() {
        return this.countdown;
    }
}
