package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.player.PlayerKickResult;

public class PlayerLoginEvent extends PlayerEvent {

    private final org.bukkit.event.player.PlayerLoginEvent source;
    private final PlayerKickResult result;

    public PlayerLoginEvent(VaroPlayer player, org.bukkit.event.player.PlayerLoginEvent loginEvent, PlayerKickResult result) {
        super(player);
        this.result = result;
        this.source = loginEvent;
    }

    public org.bukkit.event.player.PlayerLoginEvent getSource() {
        return this.source;
    }

    public PlayerKickResult getResult() {
        return this.result;
    }
}
