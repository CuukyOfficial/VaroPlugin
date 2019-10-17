package de.cuuky.varo.api.event.events.player.strike;

import de.cuuky.varo.api.event.events.player.VaroPlayerEvent;
import de.cuuky.varo.api.objects.player.stats.VaroAPIStrike;
import de.cuuky.varo.player.stats.stat.Strike;

public class PlayerStrikeRemoveEvent extends VaroPlayerEvent {

	private VaroAPIStrike strike;

	public PlayerStrikeRemoveEvent(Strike strike) {
		super(strike.getStriked(), true);

		this.strike = new VaroAPIStrike(strike);
	}

	public VaroAPIStrike getStrike() {
		return strike;
	}
}
