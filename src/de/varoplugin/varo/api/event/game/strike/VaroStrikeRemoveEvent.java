package de.varoplugin.varo.api.event.game.strike;

import de.varoplugin.varo.game.strike.Strike;

public class VaroStrikeRemoveEvent extends StrikeCancelableEvent {

    public VaroStrikeRemoveEvent(Strike strike) {
        super(strike);
    }
}