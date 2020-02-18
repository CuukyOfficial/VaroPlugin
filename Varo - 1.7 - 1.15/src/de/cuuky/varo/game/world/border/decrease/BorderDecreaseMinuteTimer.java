package de.cuuky.varo.game.world.border.decrease;

import org.bukkit.Bukkit;

import de.cuuky.varo.Main;
import de.cuuky.varo.game.state.GameState;

public class BorderDecreaseMinuteTimer {

	private int decreaseScheduler;

	public BorderDecreaseMinuteTimer() {
		if(!DecreaseReason.TIME_MINUTES.isEnabled())
			return;

		startScheduling();
	}

	private void startScheduling() {
		decreaseScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				if(Main.getVaroGame().getGameState() != GameState.STARTED || !DecreaseReason.TIME_MINUTES.isEnabled()) {
					remove();
					return;
				}
				
				Main.getVaroGame().getVaroWorld().getVaroBorder().decreaseBorder(DecreaseReason.TIME_MINUTES);
			}
		}, (DecreaseReason.TIME_MINUTES.getTime() * 60) * 20, (DecreaseReason.TIME_MINUTES.getTime() * 60) * 20);
	}

	public void remove() {
		Bukkit.getScheduler().cancelTask(decreaseScheduler);
	}
}