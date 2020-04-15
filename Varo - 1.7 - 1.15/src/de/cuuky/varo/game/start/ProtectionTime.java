package de.cuuky.varo.game.start;

import org.bukkit.Bukkit;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.messages.language.languages.ConfigMessages;

public class ProtectionTime {

	private int scheduler;

	public ProtectionTime() {
		startGeneralTimer(ConfigSetting.STARTPERIOD_PROTECTIONTIME.getValueAsInt());
	}

	public ProtectionTime(int Timer) {
		startGeneralTimer(Timer);
	}

	private void startGeneralTimer(int timer) {
		this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

			private int protectionTimer = timer;

			@Override
			public void run() {
				if(this.protectionTimer == 0) {
					Bukkit.broadcastMessage(ConfigMessages.PROTECTION_TIME_OVER.getValue());
					Main.getVaroGame().setProtection(null);
					Bukkit.getScheduler().cancelTask(scheduler);
				} else if(protectionTimer % ConfigSetting.STARTPERIOD_PROTECTIONTIME_BROADCAST_INTERVAL.getValueAsInt() == 0 && this.protectionTimer != timer)
					Bukkit.broadcastMessage(ConfigMessages.PROTECTION_TIME_UPDATE.getValue().replace("%minutes%", getCountdownMin(protectionTimer)).replace("%seconds%", getCountdownSec(protectionTimer)));

				this.protectionTimer--;
			}
		}, 1, 20);
	}

	private String getCountdownMin(int sec) {
		int min = sec / 60;

		if(min < 10)
			return "0" + min;
		else
			return min + "";
	}

	private String getCountdownSec(int sec) {
		sec = sec % 60;

		if(sec < 10)
			return "0" + sec;
		else
			return sec + "";
	}
}