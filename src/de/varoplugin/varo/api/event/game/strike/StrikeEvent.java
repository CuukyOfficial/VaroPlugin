package de.varoplugin.varo.api.event.game.strike;

import de.varoplugin.varo.api.event.game.VaroGameEvent;
import de.varoplugin.varo.game.strike.Strike;

public abstract class StrikeEvent extends VaroGameEvent {

    private final Strike strike;

    public StrikeEvent(Strike strike) {
        super(strike.getTarget().getVaro());
        this.strike = strike;
    }

    public Strike getStrike() {
        return this.strike;
    }
}