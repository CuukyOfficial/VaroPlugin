package de.varoplugin.varo.api.event.game.strike;

import de.varoplugin.varo.api.event.game.VaroGameEvent;
import de.varoplugin.varo.game.strike.VaroStrike;

public abstract class StrikeEvent extends VaroGameEvent {

    private final VaroStrike strike;

    public StrikeEvent(VaroStrike strike) {
        super(strike.getTarget().getVaro());
        this.strike = strike;
    }

    public VaroStrike getStrike() {
        return this.strike;
    }
}