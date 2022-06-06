package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.api.event.game.VaroGameCancelableEvent;
import de.varoplugin.varo.game.entity.player.VaroPlayer;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class VaroPlayerEvent extends VaroGameCancelableEvent {

    private final VaroPlayer player;

    public VaroPlayerEvent(VaroPlayer player) {
        super(player.getVaro());

        this.player = player;
    }

    public VaroPlayer getPlayer() {
        return this.player;
    }
}