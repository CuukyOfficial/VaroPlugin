package de.varoplugin.varo.api.event.game.strike;

import de.varoplugin.varo.game.strike.VaroStrike;

public class VaroStrikeInitializedEvent extends StrikeEvent {

    public VaroStrikeInitializedEvent(VaroStrike strike) {
        super(strike);
    }
}