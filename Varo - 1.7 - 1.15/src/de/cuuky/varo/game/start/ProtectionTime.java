package de.cuuky.varo.game.start;

import org.bukkit.Bukkit;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.configuration.messages.ConfigMessages;
import de.cuuky.varo.game.Game;

public class ProtectionTime {

	public ProtectionTime() {
		startGeneralTimer(ConfigEntry.STARTPERIOD_PROTECTIONTIME.getValueAsInt());
	}

	public ProtectionTime(int Timer) {
		startGeneralTimer(Timer);
	}

	private void startGeneralTimer(int timer) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				Bukkit.broadcastMessage(ConfigMessages.PROTECTION_TIME_OVER.getValue());
				Game.getInstance().setProtection(null);
			}
		}, timer * 20 + 1);
	}
}
