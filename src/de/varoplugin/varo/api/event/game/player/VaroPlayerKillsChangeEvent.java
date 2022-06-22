package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;

public class VaroPlayerKillsChangeEvent extends VaroPlayerCancelableEvent {

    private final int kills;

    public VaroPlayerKillsChangeEvent(VaroPlayer player, int kills) {
        super(player);

        this.kills = kills;
    }

    public int getKills() {
        return this.kills;
    }
}