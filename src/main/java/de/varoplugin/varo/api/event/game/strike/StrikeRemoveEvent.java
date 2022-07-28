package de.varoplugin.varo.api.event.game.strike;

import de.varoplugin.varo.game.strike.Strike;

public class StrikeRemoveEvent extends StrikeCancelableEvent {

    public StrikeRemoveEvent(Strike strike) {
        super(strike);
    }
}