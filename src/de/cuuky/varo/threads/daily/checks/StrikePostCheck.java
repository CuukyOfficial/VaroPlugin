package de.cuuky.varo.threads.daily.checks;

import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.stats.stat.Strike;
import de.cuuky.varo.threads.daily.Checker;

public class StrikePostCheck extends Checker {

	@Override
	public void check() {
		for (VaroPlayer vp : VaroPlayer.getAlivePlayer()) {
			for (Strike strike : vp.getStats().getStrikes())
				if (!strike.isPosted())
					strike.post();
		}
	}
}