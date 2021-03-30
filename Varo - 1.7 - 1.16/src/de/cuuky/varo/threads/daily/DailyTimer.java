package de.cuuky.varo.threads.daily;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.DateUtils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.recovery.recoveries.VaroBackup;
import de.cuuky.varo.threads.daily.dailycheck.Checker;
import de.cuuky.varo.threads.daily.dailycheck.checker.BloodLustCheck;
import de.cuuky.varo.threads.daily.dailycheck.checker.CoordsCheck;
import de.cuuky.varo.threads.daily.dailycheck.checker.NoJoinCheck;
import de.cuuky.varo.threads.daily.dailycheck.checker.SessionCheck;
import de.cuuky.varo.threads.daily.dailycheck.checker.StrikePostCheck;
import de.cuuky.varo.threads.daily.dailycheck.checker.YouTubeCheck;
import de.cuuky.varo.utils.VaroUtils;

public final class DailyTimer {

	private ArrayList<Checker> checker;

	public DailyTimer() {
		checker = new ArrayList<Checker>();

		checker.add(new NoJoinCheck());
		checker.add(new BloodLustCheck());
		checker.add(new SessionCheck());
		checker.add(new YouTubeCheck());
		checker.add(new CoordsCheck());
		checker.add(new StrikePostCheck());

		startTimer();
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
		reset.setHours(ConfigSetting.RESET_SESSION_HOUR.getValueAsInt());
		if (reset.before(current))
			reset = DateUtils.addDays(reset, 1);
		return (reset.getTime() - current.getTime()) / 1000;
	}

	private void startTimer() {
		VaroUtils.setWorldToTime();
		if (Main.getVaroGame().getGameState() == GameState.STARTED && Main.getVaroGame().getLastDayTimer() != null) {
			Date date = Main.getVaroGame().getLastDayTimer();
			for (int i = 0; i < getDateDiff(date, new Date(), TimeUnit.DAYS); i++) {
				if (ConfigSetting.DEBUG_OPTIONS.getValueAsBoolean())
					System.out.println("DAILY RECTIFY");

				doDailyChecks();
			}

			Main.getVaroGame().setLastDayTimer(new Date());
		}

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				try {
					new VaroBackup();
					Main.getVaroGame().setLastDayTimer(new Date());

					if (Main.getVaroGame().getGameState() == GameState.STARTED) {
						if (ConfigSetting.DEBUG_OPTIONS.getValueAsBoolean())
							System.out.println("DAILY");

						doDailyChecks();
					}

					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new BukkitRunnable() {

						@Override
						public void run() {
							startTimer();
						}
					}, 100);
				} catch (Exception e) {
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

	public void doDailyChecks() {
		for (Checker checkers : checker) {
			try {
				checkers.check();
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}

		Main.getVaroGame().setLastDayTimer(new Date());
	}
}