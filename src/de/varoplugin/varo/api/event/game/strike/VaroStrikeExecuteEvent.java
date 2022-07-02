package de.varoplugin.varo.api.event.game.strike;

import de.varoplugin.varo.game.strike.VaroStrike;

public class VaroStrikeExecuteEvent extends StrikeCancelableEvent {

    public VaroStrikeExecuteEvent(VaroStrike strike) {
        super(strike);
    }
}