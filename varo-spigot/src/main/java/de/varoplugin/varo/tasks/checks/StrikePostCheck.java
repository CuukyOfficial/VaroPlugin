package de.varoplugin.varo.tasks.checks;

import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.player.stats.stat.Strike;
import de.varoplugin.varo.tasks.Task;

public class StrikePostCheck implements Task {

	@Override
	public void check() {
		for (VaroPlayer vp : VaroPlayer.getAlivePlayer()) {
			for (Strike strike : vp.getStats().getStrikes())
				if (!strike.isPosted())
					strike.post();
		}
	}
}