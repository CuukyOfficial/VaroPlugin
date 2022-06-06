package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class VaroPlayerRemoveEvent extends VaroPlayerEvent {

    public VaroPlayerRemoveEvent(VaroPlayer player) {
        super(player);
    }
}