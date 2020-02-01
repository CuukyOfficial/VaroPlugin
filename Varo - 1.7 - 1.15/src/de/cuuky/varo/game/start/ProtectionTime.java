package de.cuuky.varo.game.start;

import org.bukkit.Bukkit;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.configuration.messages.ConfigMessages;
import de.cuuky.varo.game.Game;

public class ProtectionTime {
	
	private int scheduler;

	public ProtectionTime() {
		startGeneralTimer(ConfigEntry.STARTPERIOD_PROTECTIONTIME.getValueAsInt());
	}

	public ProtectionTime(int Timer) {
		startGeneralTimer(Timer);
	}

	private void startGeneralTimer(int timer) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

			private int protectionTimer = timer;

			@Override
			public void run() {
				if(protectionTimer == 0) {
					Bukkit.broadcastMessage(ConfigMessages.PROTECTION_TIME_OVER.getValue());
					Game.getInstance().setProtection(null);
					Bukkit.getScheduler().cancelTask(scheduler);
				} else if(protectionTimer % 60 == 0 && protectionTimer != timer)
					Bukkit.broadcastMessage(ConfigMessages.PROTECTION_TIME_UPDATE.getValue().replace("%seconds%", String.valueOf(protectionTimer / 60)));

				protectionTimer--;
			}
		}, 20, 20);
	}
}