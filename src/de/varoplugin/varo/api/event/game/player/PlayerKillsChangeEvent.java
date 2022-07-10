package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.Player;

public class PlayerKillsChangeEvent extends PlayerCancelableEvent {

    private int kills;

    public PlayerKillsChangeEvent(Player player, int kills) {
        super(player);

        this.kills = kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getKills() {
        return this.kills;
    }
}