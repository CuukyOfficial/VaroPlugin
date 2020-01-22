package de.cuuky.varo.threads;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import org.apache.commons.lang.time.DateUtils;

import de.cuuky.varo.Main;
import de.cuuky.varo.backup.Backup;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.Game;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.threads.dailycheck.Checker;
import de.cuuky.varo.utils.VaroUtils;

public final class DailyTimer {

	public static void startTimer() {
		VaroUtils.setWorldToTime();
		if(Game.getInstance().getGameState() == GameState.STARTED && Game.getInstance().getLastDayTimer() != null) {
			Date date = Game.getInstance().getLastDayTimer();
			for(int i = 0; i < getDateDiff(date, new Date(), TimeUnit.DAYS); i++) {
				if(ConfigEntry.DEBUG_OPTIONS.getValueAsBoolean())
					System.out.println("DAILY RECTIFY");

				doDailyStuff();
			}

			Game.getInstance().setLastDayTimer(new Date());
		}

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				try {
					new Backup();
					Game.getInstance().setLastDayTimer(new Date());

					if(Game.getInstance().getGameState() == GameState.STARTED) {
						if(ConfigEntry.DEBUG_OPTIONS.getValueAsBoolean())
							System.out.println("DAILY");

						doDailyStuff();
					}

					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new BukkitRunnable() {

						@Override
						public void run() {
							startTimer();
						}
					}, 100);
				} catch(Exception e) {
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new BukkitRunnable() {

						@Override
						public void run() {
							startTimer();
						}
					}, 100);
				}
			}
		}, getNextReset() * 20);
	}

	private static void doDailyStuff() {
		for(VaroPlayer vp : VaroPlayer.getVaroPlayer()) {
			vp.getStats().setCountdown(ConfigEntry.PLAY_TIME.getValueAsInt() * 60);

			if(vp.isOnline())
				vp.getPlayer().kickPlayer("RESET");
		}

		Checker.checkAll();
	}

	private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}

	@SuppressWarnings("deprecation")
	private static long getNextReset() {
		Date reset = new Date();
		reset.setMinutes(0);
		reset.setSeconds(0);
		Date current = new Date();
		reset.setHours(ConfigEntry.RESET_SESSION_HOUR.getValueAsInt());
		if(reset.before(current))
			reset = DateUtils.addDays(reset, 1);
		return (reset.getTime() - current.getTime()) / 1000;
	}
}