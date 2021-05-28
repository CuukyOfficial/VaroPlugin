package de.cuuky.varo.game.start;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

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
					Main.getLanguageManager().broadcastMessage(ConfigMessages.PROTECTION_TIME_OVER);
					Main.getVaroGame().setProtection(null);
					scheduler.cancel();
				} else if (protectionTimer % ConfigSetting.STARTPERIOD_PROTECTIONTIME_BROADCAST_INTERVAL.getValueAsInt() == 0 && this.protectionTimer != timer)
					Main.getLanguageManager().broadcastMessage(ConfigMessages.PROTECTION_TIME_UPDATE).replace("%minutes%", getCountdownMin(protectionTimer)).replace("%seconds%", getCountdownSec(protectionTimer));

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