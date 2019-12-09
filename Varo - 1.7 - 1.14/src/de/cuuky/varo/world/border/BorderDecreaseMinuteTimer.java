package de.cuuky.varo.world.border;

import de.cuuky.varo.data.DataManager;
import de.cuuky.varo.game.Game;
import de.cuuky.varo.world.WorldHandler;
import org.bukkit.Bukkit;

import de.cuuky.varo.Main;
import de.cuuky.varo.game.state.GameState;

public class BorderDecreaseMinuteTimer {

	private int sched;

	public BorderDecreaseMinuteTimer() {
		sched = -1;
		if(!DecreaseReason.TIME_MINUTES.isEnabled())
			return;

		sched = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				if(Game.getInstance().getGameState() != GameState.STARTED || !DecreaseReason.TIME_MINUTES.isEnabled()) {
					remove();
					return;
				}
				VaroBorder.getInstance().decreaseBorder(DecreaseReason.TIME_MINUTES);
			}
		}, (DecreaseReason.TIME_MINUTES.getTime() * 60) * 20, (DecreaseReason.TIME_MINUTES.getTime() * 60) * 20);
	}

	public void remove() {
		Bukkit.getScheduler().cancelTask(sched);
	}
}
