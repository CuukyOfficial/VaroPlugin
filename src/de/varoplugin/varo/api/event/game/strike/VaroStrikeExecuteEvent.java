package de.varoplugin.varo.api.event.game.strike;

import de.varoplugin.varo.game.strike.Strike;

public class VaroStrikeExecuteEvent extends StrikeCancelableEvent {

    public VaroStrikeExecuteEvent(Strike strike) {
        super(strike);
    }
}