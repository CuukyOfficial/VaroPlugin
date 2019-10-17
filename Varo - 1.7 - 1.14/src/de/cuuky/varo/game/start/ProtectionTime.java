package de.cuuky.varo.game.start;

import org.bukkit.Bukkit;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.config.messages.ConfigMessages;

public class ProtectionTime {

	public ProtectionTime() {
		startTimer();
	}

	private void startTimer() {
		Bukkit.broadcastMessage(ConfigMessages.PROTECTION_START.getValue().replace("%seconds%", String.valueOf(ConfigEntry.STARTPERIOD_PROTECTIONTIME.getValueAsInt())));
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				Bukkit.broadcastMessage(ConfigMessages.PROTECTION_TIME_OVER.getValue());
				Main.getGame().setProtection(null);
			}
		}, ConfigEntry.STARTPERIOD_PROTECTIONTIME.getValueAsInt() * 20 + 1);
	}
}
