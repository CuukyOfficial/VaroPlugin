package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.player.VaroPlayerKickResult;
import org.bukkit.event.player.PlayerLoginEvent;

public class VaroPlayerLoginEvent extends VaroPlayerEvent {

    private final PlayerLoginEvent source;
    private final VaroPlayerKickResult result;

    public VaroPlayerLoginEvent(VaroPlayer player, PlayerLoginEvent loginEvent, VaroPlayerKickResult result) {
        super(player);
        this.result = result;
        this.source = loginEvent;
    }

    public PlayerLoginEvent getSource() {
        return this.source;
    }

    public VaroPlayerKickResult getResult() {
        return this.result;
    }
}
