package de.varoplugin.varo.tasks;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.tasks.checks.*;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.bukkit.Bukkit;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public final class DailyTasks {

	private final List<Task> tasks;

	public DailyTasks() {
		this.tasks = Arrays.asList(new NoJoinCheck(), new BloodLustCheck(), new SessionCheck(), new YouTubeCheck(), new CoordsCheck(), new StrikePostCheck());

		catchUp();
		startTimer();
	}

	private void catchUp() {
		if (!Main.getVaroGame().isRunning())
			return;

		ImmutablePair<Long, OffsetDateTime> catchUp = getCatchUp(OffsetDateTime.now(), Main.getVaroGame().getLastDayTimer(),
				Main.getVaroGame().getStartTimestamp(), ConfigSetting.RESET_SESSION_HOUR.getValueAsInt());
		if (catchUp.getLeft() == 0)
			return;

		Main.getInstance().getLogger().log(Level.INFO, "Catching up on {0} daily tasks...", catchUp.getLeft());
		for (long i = 0; i < catchUp.getLeft(); i++)
			doDailyTasks();
		Main.getVaroGame().setLastDayTimer(catchUp.getRight());
	}

	private void startTimer() {
		OffsetDateTime now = OffsetDateTime.now();
		OffsetDateTime next = getNextRun(now, Main.getVaroGame().getLastDayTimer(), ConfigSetting.RESET_SESSION_HOUR.getValueAsInt());
		Main.getInstance().getLogger().log(Level.INFO, "Next daily task: " + next);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
			try {
				Main.getInstance().getLogger().log(Level.INFO, "Running daily timer...");
				Main.getDataManager().createBackup(null);

				if (Main.getVaroGame().isRunning()) {
					doDailyTasks();
					Main.getVaroGame().setLastDayTimer(next);
				}
			} catch (Throwable t) {
				Main.getInstance().getLogger().log(Level.SEVERE, "Error while running daily timer", t);
			}

			startTimer();
		}, now.until(next, ChronoUnit.SECONDS) * 20L);
	}

	public void doDailyTasks() {
	    Main.getInstance().getLogger().log(Level.INFO, "Running daily tasks...");
		for (Task checkers : this.tasks) {
			try {
				checkers.check();
			} catch (Throwable t) {
				Main.getInstance().getLogger().log(Level.SEVERE, "Error while running daily tasks", t);
			}
		}
	}

	/**
	 * Returns the timestamp of the next run.
	 *
	 * @param now the current date-time
	 * @param prev the timestamp of the previous run, possibly null
	 * @param resetHour the reset hour
	 * @return the timestamp of the next run
	 * @throws IllegalArgumentException if resetHour is less than 0 or greater than 23
	 */
	public static OffsetDateTime getNextRun(OffsetDateTime now, OffsetDateTime prev, int resetHour) {
		if (resetHour < 0 || resetHour > 23)
			throw new IllegalArgumentException("Invalid reset hour: " + resetHour);

		OffsetDateTime next = prev;
		if (prev == null) {
			next = now.withHour(resetHour).withMinute(0).withSecond(0).withNano(0);
			if (next.isAfter(now))
				return next;
		}
		next = next.plusDays(1);
		return next;
	}

	/**
	 * Returns an {@link ImmutablePair} containing the number of missed runs and the new timestamp of the last run.
	 *
	 * @param now the current date-time
	 * @param prev the timestamp of the previous run, possibly null
	 * @param start the timestamp of the project start
	 * @param resetHour the reset hour
	 * @return an {@link ImmutablePair} containing the number of missed runs and the new timestamp of the last run
	 * @throws IllegalArgumentException if resetHour is less than 0 or greater than 23
	 */
	public static ImmutablePair<Long, OffsetDateTime> getCatchUp(OffsetDateTime now, OffsetDateTime prev, OffsetDateTime start, int resetHour) {
		if (resetHour < 0 || resetHour > 23)
			throw new IllegalArgumentException("Invalid reset hour: " + resetHour);

		OffsetDateTime prevRun = prev;
		if (prevRun == null) {
			prevRun = start.withHour(resetHour).withMinute(0).withSecond(0).withNano(0);
			if (prevRun.isAfter(start))
				prevRun = prevRun.minusDays(1);
		}

		long days = Duration.between(prevRun, now).toDays();
		return new ImmutablePair<>(days, prevRun.plusDays(days));
	}
}