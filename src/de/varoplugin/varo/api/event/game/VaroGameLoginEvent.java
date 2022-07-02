package de.varoplugin.varo.api.event.game;

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.VaroKickResult;
import org.bukkit.event.player.PlayerLoginEvent;

public class VaroGameLoginEvent extends VaroGameEvent {

    private final PlayerLoginEvent source;
    private final VaroKickResult result;

    public VaroGameLoginEvent(Varo varo, PlayerLoginEvent loginEvent, VaroKickResult result) {
        super(varo);

        this.source = loginEvent;
        this.result = result;
    }

    public PlayerLoginEvent getSource() {
        return this.source;
    }

    public VaroKickResult getResult() {
        return this.result;
    }
}