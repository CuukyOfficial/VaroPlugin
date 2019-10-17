package de.cuuky.varo.world;

import org.bukkit.Bukkit;
import org.bukkit.World;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.config.ConfigEntry;

public class TimeTimer {

	public TimeTimer() {
		if(!ConfigEntry.ALWAYS_TIME.isIntActivated())
			return;

		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

			int time = ConfigEntry.ALWAYS_TIME.getValueAsInt();

			@Override
			public void run() {
				if(Main.getGame().isStarted() && !ConfigEntry.ALWAYS_TIME_USE_AFTER_START.getValueAsBoolean())
					return;

				for(World world : Bukkit.getWorlds()) {
					world.setTime(time);
					world.setThundering(false);
					world.setStorm(false);
				}
			}
		}, 0, 40);
	}
}
