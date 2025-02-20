package de.cuuky.varo.game.start;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import io.github.almightysatan.slams.PlaceholderResolver;

public class ProtectionTime {

	private BukkitTask scheduler;

	public ProtectionTime() {
		startGeneralTimer(ConfigSetting.STARTPERIOD_PROTECTIONTIME.getValueAsInt());
	}

	public ProtectionTime(int timer) {
		startGeneralTimer(timer);
	}

	private void startGeneralTimer(int timer) {
		this.scheduler = new BukkitRunnable() {

			private int protectionTimer = timer;

			@Override
			public void run() {
				if (!Main.getVaroGame().isRunning()) {
					scheduler.cancel();
					return;
				}

				if (this.protectionTimer == 0) {
					Messages.PROTECTION_END.broadcast();
					Main.getVaroGame().setProtection(null);
					scheduler.cancel();
				} else if (protectionTimer % ConfigSetting.STARTPERIOD_PROTECTIONTIME_BROADCAST_INTERVAL.getValueAsInt() == 0 && this.protectionTimer != timer)
				    Messages.PROTECTION_PROTECTED.broadcast(PlaceholderResolver.builder().constant("protection-minutes", getCountdownMin(protectionTimer))
				            .constant("protection-seconds", getCountdownSec(protectionTimer)).build());

				this.protectionTimer--;
			}
		}.runTaskTimer(Main.getInstance(), 1L, 20L);
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
}