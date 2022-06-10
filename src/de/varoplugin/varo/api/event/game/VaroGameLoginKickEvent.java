package de.varoplugin.varo.api.event.game;

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.VaroKickResult;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class VaroGameLoginKickEvent extends VaroGameCancelableEvent {

    private final PlayerLoginEvent loginEvent;
    private final VaroKickResult result;

    public VaroGameLoginKickEvent(Varo varo, PlayerLoginEvent loginEvent, VaroKickResult result) {
        super(varo);

        this.loginEvent = loginEvent;
        this.result = result;
    }

    public PlayerLoginEvent getLoginEvent() {
        return this.loginEvent;
    }

    public VaroKickResult getResult() {
        return this.result;
    }
}