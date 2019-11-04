package de.cuuky.varo.api.event.events.player.strike;

import de.cuuky.varo.api.event.events.player.VaroPlayerEvent;
import de.cuuky.varo.api.objects.player.stats.VaroAPIStrike;
import de.cuuky.varo.entity.player.stats.stat.Strike;

public class PlayerStrikeReceiveEvent extends VaroPlayerEvent {

	private VaroAPIStrike strike;

	public PlayerStrikeReceiveEvent(Strike strike) {
		super(strike.getStriked(), false);

		this.strike = new VaroAPIStrike(strike);
	}

	public VaroAPIStrike getStrike() {
		return strike;
	}
}
