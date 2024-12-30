package de.cuuky.varo.game.start;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;

public class ProtectionTime {

	private BukkitTask scheduler;
	private int protectionTimer;

	public ProtectionTime() {
		startGeneralTimer(ConfigSetting.STARTPERIOD_PROTECTIONTIME.getValueAsInt());
	}

	public ProtectionTime(int timer) {
		startGeneralTimer(timer);
	}

	private void startGeneralTimer(int timer) {
		this.protectionTimer = timer;
		this.scheduler = new BukkitRunnable() {

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
				} else if (ProtectionTime.this.protectionTimer % ConfigSetting.STARTPERIOD_PROTECTIONTIME_BROADCAST_INTERVAL.getValueAsInt() == 0) {
					Main.getLanguageManager().broadcastMessage(ConfigMessages.PROTECTION_TIME_UPDATE).replace("%minutes%", getCountdownMin(ProtectionTime.this.protectionTimer)).replace("%seconds%", getCountdownSec(ProtectionTime.this.protectionTimer));
				}

				ProtectionTime.this.protectionTimer--;
			}
		}.runTaskTimer(Main.getInstance(), 1L, 20L);
	}

	private String getCountdownMin(int sec) {
		int min = sec / 60;
		return (min < 10) ? "0" + min : String.valueOf(min);
	}

	private String getCountdownSec(int sec) {
		sec = sec % 60;
		return (sec < 10) ? "0" + sec : String.valueOf(sec);
	}

	/**
	*
	* @return the countdown in minutes
	*/
	public String getCountdownMinutes() {
		return getCountdownMin(this.protectionTimer);
	}

	/**
	*
	* @return the count down in seconds
	*/
	public String getCountdownSeconds() {
		return getCountdownSec(this.protectionTimer);
	}

	public int getRemainingTime() {
		return this.protectionTimer;
	}

	public int getProtectionTimer() {
		return protectionTimer;
	}
}
