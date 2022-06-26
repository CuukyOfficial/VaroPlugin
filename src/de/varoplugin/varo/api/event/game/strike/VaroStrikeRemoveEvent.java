package de.varoplugin.varo.api.event.game.strike;

import de.varoplugin.varo.game.strike.VaroStrike;

public class VaroStrikeRemoveEvent extends StrikeCancelableEvent {

    public VaroStrikeRemoveEvent(VaroStrike strike) {
        super(strike);
    }
}