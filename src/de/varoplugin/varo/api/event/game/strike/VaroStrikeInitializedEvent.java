package de.varoplugin.varo.api.event.game.strike;

import de.varoplugin.varo.game.strike.Strike;

public class VaroStrikeInitializedEvent extends StrikeEvent {

    public VaroStrikeInitializedEvent(Strike strike) {
        super(strike);
    }
}