package de.varoplugin.varo.threads.daily.checks;

import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.player.stats.stat.Strike;
import de.varoplugin.varo.threads.daily.Checker;

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