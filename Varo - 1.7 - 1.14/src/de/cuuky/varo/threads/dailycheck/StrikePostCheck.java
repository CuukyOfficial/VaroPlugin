package de.cuuky.varo.threads.dailycheck;

import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.stats.stat.Strike;

public class StrikePostCheck extends Checker {

	@Override
	public void check() {
		for(VaroPlayer vp : VaroPlayer.getAlivePlayer()) {
			for(Strike strike : vp.getStats().getStrikes())
				if(!strike.isPosted())
					strike.post();
		}
	}
}
