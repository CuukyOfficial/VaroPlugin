package de.cuuky.varo.threads.daily.dailycheck.checker;

import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.Strike;
import de.cuuky.varo.threads.daily.dailycheck.Checker;

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