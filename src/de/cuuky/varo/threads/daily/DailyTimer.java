package de.cuuky.varo.threads.daily;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
		
		startTimer(0L);
	}

	private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}

	private static long getNextReset(long offset) {
	    OffsetDateTime reset = OffsetDateTime.now().withHour(ConfigSetting.RESET_SESSION_HOUR.getValueAsInt()).withMinute(0).withSecond(0).withNano(0);
		long resetTime = reset.toInstant().toEpochMilli();
	    long time = System.currentTimeMillis();
		if (resetTime <= (time + offset))
		    resetTime = reset.plusDays(1).toInstant().toEpochMilli();
		return (resetTime - time) / 1000L;
	}

	private void startTimer(long offset) {
		if (Main.getVaroGame().isRunning() && Main.getVaroGame().getLastDayTimer() != null) {
			Date date = Main.getVaroGame().getLastDayTimer();
			long dateDiff = getDateDiff(date, new Date(), TimeUnit.DAYS);
			for (long i = 0; i < dateDiff; i++) {
			    System.out.println(Main.getConsolePrefix() + "Catching up with daily tasks...");
				doDailyChecks();
			}

			Main.getVaroGame().setLastDayTimer(new Date());
		}

		long nextTaskSeconds = getNextReset(offset);
		System.out.println(Main.getConsolePrefix() + "Next daily task: " + nextTaskSeconds);
		new BukkitRunnable() {
			@Override
			public void run() {
				try {
				    System.out.println(Main.getConsolePrefix() + "Running daily timer...");
				    
					new VaroBackup();
					Main.getVaroGame().setLastDayTimer(new Date());

					if (Main.getVaroGame().isRunning())
						doDailyChecks();
				} catch (Throwable e) {
					e.printStackTrace();
				}

				startTimer(6L * 60L * 60L * 1000L);
			}
		}.runTaskLater(Main.getInstance(), nextTaskSeconds * 20L);
	}

	public void doDailyChecks() {
	    System.out.println(Main.getConsolePrefix() + "Running daily checks...");
		for (Checker checkers : checker) {
			try {
				checkers.check();
			} catch (Throwable t) {
				t.printStackTrace();
				continue;
			}
		}

		Main.getVaroGame().setLastDayTimer(new Date());
	}
}