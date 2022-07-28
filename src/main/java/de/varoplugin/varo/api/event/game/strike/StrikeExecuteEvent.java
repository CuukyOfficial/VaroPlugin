package de.varoplugin.varo.api.event.game.strike;

import de.varoplugin.varo.game.strike.Strike;

public class StrikeExecuteEvent extends StrikeCancelableEvent {

    public StrikeExecuteEvent(Strike strike) {
        super(strike);
    }
}