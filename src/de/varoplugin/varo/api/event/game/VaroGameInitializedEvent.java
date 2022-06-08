package de.varoplugin.varo.api.event.game;

import de.varoplugin.varo.game.Varo;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class VaroGameInitializedEvent extends VaroGameEvent {

    public VaroGameInitializedEvent(Varo varo) {
        super(varo);
    }
}