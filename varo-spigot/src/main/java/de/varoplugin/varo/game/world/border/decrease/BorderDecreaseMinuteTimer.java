package de.varoplugin.varo.game.world.border.decrease;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import io.github.almightysatan.slams.Placeholder;
import io.github.almightysatan.slams.PlaceholderResolver;

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
				if (!Main.getVaroGame().isRunning() && !Main.getVaroGame().isStarting() || !DecreaseReason.TIME_MINUTES.isEnabled()) {
					remove();
					return;
				}

				if (secondsPassed == 0) {
					Main.getVaroGame().getVaroWorldHandler().decreaseBorder(DecreaseReason.TIME_MINUTES);
					secondsPassed = timer;
				} else if (secondsPassed % ConfigSetting.BORDER_TIME_MINUTE_BC_INTERVAL.getValueAsInt() == 0 && secondsPassed != timer)
					Messages.BORDER_MINUTE_TIME_UPDATE.broadcast(PlaceholderResolver.of(Placeholder.constant("border-decrease-minutes", getCountdownMin(secondsPassed)), Placeholder.constant("border-decrease-seconds", getCountdownSec(secondsPassed))));
				
				secondsPassed--;
			}
		}.runTaskTimer(Main.getInstance(), 20L, 20L);
	}

	private String getCountdownMin(int sec) {
		int min = sec / 60;

		if (min < 10)
			return "0" + min;
        return min + "";
	}

	private String getCountdownSec(int sec) {
		sec = sec % 60;

		if (sec < 10)
			return "0" + sec;
        return sec + "";
	}

	public void remove() {
		if (decreaseScheduler != null)
		    decreaseScheduler.cancel();
	}
}