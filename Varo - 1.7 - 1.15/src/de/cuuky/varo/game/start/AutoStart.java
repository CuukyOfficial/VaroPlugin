package de.cuuky.varo.game.start;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;

import org.apache.commons.lang.time.DateUtils;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.BotLauncher;
import de.cuuky.varo.game.Game;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;
import de.cuuky.varo.utils.JavaUtils;

public class AutoStart implements VaroSerializeable {

	private int sched;
	@VaroSerializeField(path = "start")
	private Date start;

	public AutoStart() {
		Game.getInstance().setAutoStart(this);
	}

	public AutoStart(Calendar start) {
		this.start = start.getTime();
		this.start.setSeconds(0);

		start();
		Game.getInstance().setAutoStart(this);

		postMessage(Main.getProjectName() + " §7wird am " + Main.getColorCode() + getDayByInt(start.get(Calendar.DAY_OF_WEEK)) + " §7den " + Main.getColorCode() + getWithZero(start.get(Calendar.DAY_OF_MONTH)) + "§7." + Main.getColorCode() + getWithZero(start.get(Calendar.MONTH) + 1) + "§7." + Main.getColorCode() + start.get(Calendar.YEAR) + " §7um " + Main.getColorCode() + getWithZero(start.get(Calendar.HOUR_OF_DAY)) + "§7:" + Main.getColorCode() + getWithZero(start.get(Calendar.MINUTE)) + " §7starten!");
	}

	private long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}

	private String getDayByInt(int i) {
		switch(i) {
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
		if(BotLauncher.getDiscordBot() != null && BotLauncher.getDiscordBot().isEnabled() && BotLauncher.getDiscordBot().getAnnouncementChannel() != null)
			BotLauncher.getDiscordBot().sendRawMessage(JavaUtils.replaceAllColors(message) + " " + BotLauncher.getDiscordBot().getMentionRole(), BotLauncher.getDiscordBot().getAnnouncementChannel());
		Bukkit.broadcastMessage(message);
	}

	@SuppressWarnings("deprecation")
	private void start() {
		if(new Date().after(start)) {
			stop();
			return;
		}

		Calendar current = new GregorianCalendar();
		long delay = getDateDiff(current.getTime(), start, TimeUnit.MILLISECONDS);
		StartDelay startDelay = StartDelay.getStartDelay(delay);
		long seconds = (long) (delay - startDelay.getDelay());

		this.sched = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch(InterruptedException e) {}

				Bukkit.getScheduler().cancelTask(sched);
				if(startDelay == StartDelay.GO) {
					stop();
					Game.getInstance().start();
					Bukkit.broadcastMessage(Main.getProjectName() + " §7wird gestartet...");
					return;
				}

				postMessage(Main.getProjectName() + " §7startet in " + startDelay.getFormated("§7") + "!");

				start();
			}
		}, (seconds / 1000) * 20);
	}

	public void delay(int seconds) {
		Bukkit.getScheduler().cancelTask(sched);
		this.start = DateUtils.addMinutes(this.start, seconds);
		StartDelay.reset();
		start();
	}
	
	public void stop() {
		Bukkit.getScheduler().cancelTask(sched);
		Game.getInstance().setAutoStart(null);
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