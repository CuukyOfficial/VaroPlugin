package de.varoplugin.varo.api.event.game.strike;

import de.varoplugin.varo.game.strike.VaroStrike;

public class VaroStrikeEvent extends StrikeCancelableEvent {

    public VaroStrikeEvent(VaroStrike strike) {
        super(strike);
    }
}