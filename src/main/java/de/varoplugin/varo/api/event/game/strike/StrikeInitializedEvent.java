package de.varoplugin.varo.api.event.game.strike;

import de.varoplugin.varo.game.strike.Strike;

public class StrikeInitializedEvent extends AbstractStrikeEvent {

    public StrikeInitializedEvent(Strike strike) {
        super(strike);
    }
}