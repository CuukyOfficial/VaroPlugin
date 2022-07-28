package de.varoplugin.varo.api.event.game.strike;

import de.varoplugin.varo.game.strike.Strike;

public class StrikeEvent extends StrikeCancelableEvent {

    public StrikeEvent(Strike strike) {
        super(strike);
    }
}