package de.cuuky.varo.game.start;

import java.util.Calendar;
import java.util.GregorianCalendar;

public enum StartDelay {

	DAY(86400, "Tag", "einem"),
	FIVE_MINTUES(60 * 5, "Minuten", "fünf"),
	FOUR_MINTUES(60 * 4, "Minuten", "vier"),
	GO(0, null, null),
	HALF_HOUR(1800, "Stunde", "einer halben"),
	HOUR(3600, "Stunde", "einer"),
	MINTUE(60, "Minute", "einer"),
	MONTH(new GregorianCalendar().getActualMaximum(Calendar.DAY_OF_MONTH) * 86400, "Monat", "einem"),
	TEN_MINTUES(60 * 10, "Minuten", "zehn"),
	THREE_MINTUES(60 * 3, "Minuten", "drei"),
	TWO_MINTUES(60 * 2, "Minuten", "zwei"),
	WEEK(604800, "Woche", "einer");

	private String article, unit;
	private double delay;
	private boolean used;

	private StartDelay(double delay, String unit, String article) {
		this.delay = delay * 1000;
		this.unit = unit;
		this.article = article;
	}

	public double getDelay() {
		return delay;
	}

	public String getFormated(String insert) {
		return article + " " + insert + unit;
	}

	public String getUnit() {
		return unit;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public static StartDelay getStartDelay(long delay) {
		for(StartDelay sd : values()) {
			if(!(delay >= sd.getDelay()))
				continue;

			if(sd.isUsed())
				continue;

			sd.setUsed(true);
			return sd;
		}

		return null;
	}

	public static void reset() {
		for(StartDelay sd : values())
			sd.setUsed(false);
	}
}