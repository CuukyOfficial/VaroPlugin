package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.Player;

public class VaroPlayerCountdownChangeEvent extends VaroPlayerCancelableEvent {

    private int countdown;

    public VaroPlayerCountdownChangeEvent(Player player, int countdown) {
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
