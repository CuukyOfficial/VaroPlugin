package de.cuuky.varo.game.start;

import java.util.Calendar;
import java.util.GregorianCalendar;

public enum StartDelay {

	MONTH(new GregorianCalendar().getActualMaximum(Calendar.DAY_OF_MONTH) * 86400, "Monat", "einem"),
	WEEK(604800, "Woche", "einer"),
	DAY(86400, "Tag", "einem"),
	HOUR(3600, "Stunde", "einer"),
	HALF_HOUR(1800, "Stunde", "einer halben"),
	TEN_MINTUES(60 * 10, "Minuten", "zehn"),
	FIVE_MINTUES(60 * 5, "Minuten", "fünf"),
	FOUR_MINTUES(60 * 4, "Minuten", "vier"),
	THREE_MINTUES(60 * 3, "Minuten", "drei"),
	TWO_MINTUES(60 * 2, "Minuten", "zwei"),
	MINTUE(60, "Minute", "einer"),
	GO(0, null, null);

	private double delay;
	private String unit;
	private String article;
	private boolean used;

	private StartDelay(double delay, String unit, String article) {
		this.delay = delay * 1000;
		this.unit = unit;
		this.article = article;
		this.used = false;
	}

	public String getFormated(String insert) {
		return article + " " + insert + unit;
	}

	public double getDelay() {
		return delay;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public boolean isUsed() {
		return used;
	}

	public String getUnit() {
		return unit;
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