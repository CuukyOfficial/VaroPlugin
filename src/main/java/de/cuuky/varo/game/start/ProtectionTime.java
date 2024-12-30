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

		if (this.protectionTimer == 0) {
			Main.getVaroGame().setProtection(null);
			return;
		}
		
		this.scheduler = new BukkitRunnable() {

			@Override
			public void run() {
				if (!Main.getVaroGame().isRunning()) {
					scheduler.cancel();
					return;
				}

				if (ProtectionTime.this.protectionTimer == 0) {
					Main.getLanguageManager().broadcastMessage(ConfigMessages.PROTECTION_TIME_OVER);
					Main.getVaroGame().setProtection(null);
					scheduler.cancel();
				} else if (ProtectionTime.this.protectionTimer % ConfigSetting.STARTPERIOD_PROTECTIONTIME_BROADCAST_INTERVAL.getValueAsInt() == 0) {
					Main.getLanguageManager().broadcastMessage(ConfigMessages.PROTECTION_TIME_UPDATE)
						.replace("%minutes%", getCountdownMin(ProtectionTime.this.protectionTimer))
						.replace("%seconds%", getCountdownSec(ProtectionTime.this.protectionTimer));
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
	* @return the countdown in minutes or null if no protection time is set
	*/
	public String getCountdownMinutes() {
		return this.protectionTimer > 0 ? getCountdownMin(this.protectionTimer) : null;
	}

	/**
	*
	* @return the countdown in seconds or null if no protection time is set
	*/
	public String getCountdownSeconds() {
		return this.protectionTimer > 0 ? getCountdownSec(this.protectionTimer) : null;
	}

	/**
	*
	* @return the remaining protection time or null if no protection time is set
	*/
	public Integer getRemainingTime() {
		return this.protectionTimer > 0 ? this.protectionTimer : null;
	}

	/**
	*
	* @return the protection timer or null if no protection time is set
	*/
	public Integer getProtectionTimer() {
		return this.protectionTimer > 0 ? protectionTimer : null;
	}
}
