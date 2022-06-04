package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.api.event.game.VaroGameCancelableEvent;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.player.VaroPlayer;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class VaroPlayerEvent extends VaroGameCancelableEvent {

    private final VaroPlayer player;

    public VaroPlayerEvent(Varo varo, VaroPlayer player) {
        super(varo);

        this.player = player;
    }

    public VaroPlayer getPlayer() {
        return this.player;
    }
}