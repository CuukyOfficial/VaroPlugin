package de.varoplugin.varo.api.event.game.strike;

import de.varoplugin.varo.game.strike.Strike;

public class VaroStrikeEvent extends StrikeCancelableEvent {

    public VaroStrikeEvent(Strike strike) {
        super(strike);
    }
}