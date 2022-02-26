package de.cuuky.varo.game.start;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.DateUtils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.cuuky.cfw.utils.JavaUtils;
import de.cuuky.varo.app.Main;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;

public class AutoStart implements VaroSerializeable {

	private BukkitTask sched;
	@Serialize("start")
	private Date start;

	public AutoStart() {
		Main.getVaroGame().setAutoStart(this);
	}

	public AutoStart(Calendar start) {
		this.start = start.getTime();
		this.start.setSeconds(0);

		start();
		Main.getVaroGame().setAutoStart(this);

		postMessage(Main.getProjectName() + " §7wird am " + Main.getColorCode() + getDayByInt(start.get(Calendar.DAY_OF_WEEK)) + " §7den " + Main.getColorCode() + getWithZero(start.get(Calendar.DAY_OF_MONTH)) + "§7." + Main.getColorCode() + getWithZero(start.get(Calendar.MONTH) + 1) + "§7." + Main.getColorCode() + start.get(Calendar.YEAR) + " §7um " + Main.getColorCode() + getWithZero(start.get(Calendar.HOUR_OF_DAY)) + "§7:" + Main.getColorCode() + getWithZero(start.get(Calendar.MINUTE)) + " §7starten!");
	}

	private long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}

	private String getDayByInt(int i) {
		switch (i) {
		case 1:
			return "Sonntag";
		case 2:
			return "Montag";
		case 3:
			return "Dienstag";
		case 4:
			return "Mittwoch";
		case 5:
			return "Donnerstag";
		case 6:
			return "Freitag";
		case 7:
			return "Samstag";
		default:
			break;
		}
		return null;
	}

	private String getWithZero(int i) {
		return i < 10 ? "0" + i : String.valueOf(i);
	}

	private void postMessage(String message) {
		if (Main.getBotLauncher().getDiscordbot() != null && Main.getBotLauncher().getDiscordbot().isEnabled() && Main.getBotLauncher().getDiscordbot().getAnnouncementChannel() != null)
			Main.getBotLauncher().getDiscordbot().sendRawMessage(JavaUtils.replaceAllColors(message) + " " + Main.getBotLauncher().getDiscordbot().getMentionRole(), Main.getBotLauncher().getDiscordbot().getAnnouncementChannel());
		Bukkit.broadcastMessage(message);
	}

	@SuppressWarnings("deprecation")
	private void start() {
		if (new Date().after(start)) {
			stop();
			return;
		}

		Calendar current = new GregorianCalendar();
		long delay = getDateDiff(current.getTime(), start, TimeUnit.MILLISECONDS);
		StartDelay startDelay = StartDelay.getStartDelay(delay);
		long seconds = (long) (delay - startDelay.getDelay());

		this.sched = new BukkitRunnable() {
			@Override
			public void run() {
				sched.cancel();
				if (startDelay == StartDelay.GO) {
					stop();
					Main.getVaroGame().prepareStart();
					Bukkit.broadcastMessage(Main.getProjectName() + " §7wird gestartet...");
					return;
				}

				postMessage(Main.getProjectName() + " §7startet in " + startDelay.getFormated("§7") + "!");

				start();
			}
		}.runTaskLater(Main.getInstance(), (seconds / 1000) * 20 + 20);
	}

	public void delay(int seconds) {
		sched.cancel();
		this.start = DateUtils.addMinutes(this.start, seconds);
		StartDelay.reset();
		start();
	}

	public void stop() {
		sched.cancel();
		Main.getVaroGame().setAutoStart(null);
		StartDelay.reset();
	}

	@Override
	public void onDeserializeEnd() {
		start();
	}

	@Override
	public void onSerializeStart() {}

	public Date getStart() {
		return start;
	}
}