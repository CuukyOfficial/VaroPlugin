package de.cuuky.varo.threads;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.DateUtils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import de.cuuky.varo.Main;
import de.cuuky.varo.backup.Backup;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.threads.dailycheck.Checker;
import de.cuuky.varo.world.TimeTimer;

public class DailyTimer {

	public DailyTimer() {
		new TimeTimer();
		if(Main.getGame().getGameState() == GameState.STARTED && Main.getGame().getLastDayTimer() != null) {
			Date date = Main.getGame().getLastDayTimer();
			for(int i = 0; i < getDateDiff(date, new Date(), TimeUnit.DAYS); i++) {
				if(ConfigEntry.DEBUG_OPTIONS.getValueAsBoolean())
					System.out.println("DAILY RE");

				doDailyStuff();
			}

			Main.getGame().setLastDayTimer(new Date());
		}

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				try {
					new Backup();
					Main.getGame().setLastDayTimer(new Date());

					if(Main.getGame().getGameState() == GameState.STARTED) {
						if(ConfigEntry.DEBUG_OPTIONS.getValueAsBoolean())
							System.out.println("DAILY");

						doDailyStuff();
					}

					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new BukkitRunnable() {

						@Override
						public void run() {
							new DailyTimer();
						}
					}, 100);
				} catch(Exception e) {
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new BukkitRunnable() {

						@Override
						public void run() {
							new DailyTimer();
						}
					}, 100);
				}
			}
		}, getNextReset() * 20);
	}

	private void doDailyStuff() {
		for(VaroPlayer vp : VaroPlayer.getVaroPlayer()) {
			vp.getStats().setCountdown(ConfigEntry.PLAY_TIME.getValueAsInt() * 60);

			if(vp.isOnline())
				vp.getPlayer().kickPlayer("RESET");
		}

		Checker.checkAll();
	}

	@SuppressWarnings("deprecation")
	private long getNextReset() {
		Date reset = new Date();
		reset.setMinutes(0);
		reset.setSeconds(0);
		Date current = new Date();
		reset.setHours(ConfigEntry.RESET_SESSION_HOUR.getValueAsInt());
		if(reset.before(current))
			reset = DateUtils.addDays(reset, 1);
		return (reset.getTime() - current.getTime()) / 1000;
	}

	private long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}
}
