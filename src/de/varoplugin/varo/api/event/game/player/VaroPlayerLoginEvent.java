package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.Player;
import de.varoplugin.varo.game.entity.player.PlayerKickResult;
import org.bukkit.event.player.PlayerLoginEvent;

public class VaroPlayerLoginEvent extends VaroPlayerEvent {

    private final PlayerLoginEvent source;
    private final PlayerKickResult result;

    public VaroPlayerLoginEvent(Player player, PlayerLoginEvent loginEvent, PlayerKickResult result) {
        super(player);
        this.result = result;
        this.source = loginEvent;
    }

    public PlayerLoginEvent getSource() {
        return this.source;
    }

    public PlayerKickResult getResult() {
        return this.result;
    }
}
