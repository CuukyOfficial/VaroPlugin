package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.player.VaroPlayer;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class VaroPlayerRemoveEvent extends VaroPlayerEvent {

    public VaroPlayerRemoveEvent(Varo varo, VaroPlayer player) {
        super(varo, player);
    }
}