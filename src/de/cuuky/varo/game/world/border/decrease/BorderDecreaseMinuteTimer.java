package de.cuuky.varo.game.world.border.decrease;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.game.state.GameState;

public class BorderDecreaseMinuteTimer {

	private BukkitTask decreaseScheduler;
	private int secondsPassed, timer;

	public BorderDecreaseMinuteTimer() {
		if (!DecreaseReason.TIME_MINUTES.isEnabled())
			return;

		this.timer = DecreaseReason.TIME_MINUTES.getTime() * 60;
		this.secondsPassed = timer;
		startScheduling();
	}

	private void startScheduling() {
		decreaseScheduler = new BukkitRunnable() {
			@Override
			public void run() {
				if (Main.getVaroGame().getGameState() != GameState.STARTED && !Main.getVaroGame().isStarting() || !DecreaseReason.TIME_MINUTES.isEnabled()) {
					remove();
					return;
				}

				if (secondsPassed == 0) {
					Main.getVaroGame().getVaroWorldHandler().decreaseBorder(DecreaseReason.TIME_MINUTES);
					secondsPassed = timer;
				} else if (secondsPassed % ConfigSetting.BORDER_TIME_MINUTE_BC_INTERVAL.getValueAsInt() == 0 && secondsPassed != timer)
					Main.getLanguageManager().broadcastMessage(ConfigMessages.BORDER_MINUTE_TIME_UPDATE).replace("%minutes%", getCountdownMin(secondsPassed)).replace("%seconds%", getCountdownSec(secondsPassed)).replace("%size%", String.valueOf(ConfigSetting.BORDER_TIME_MINUTE_DECREASE_SIZE.getValueAsInt()));

				secondsPassed--;
			}
		}.runTaskTimer(Main.getInstance(), 20L, 20L);
	}

	private String getCountdownMin(int sec) {
		int min = sec / 60;

		if (min < 10)
			return "0" + min;
		else
			return min + "";
	}

	private String getCountdownSec(int sec) {
		sec = sec % 60;

		if (sec < 10)
			return "0" + sec;
		else
			return sec + "";
	}

	public void remove() {
		if (decreaseScheduler != null)
		    decreaseScheduler.cancel();
	}
}