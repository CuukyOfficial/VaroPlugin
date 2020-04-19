package de.cuuky.varo.game.start;

public enum StartDelay {

	WEEK(604800, "Woche", "einer"),
	DAY(86400, "Tag", "einem"),
	HOUR(3600, "Stunde", "einer"),
	HALF_HOUR(1800, "Stunde", "einer halben"),
	TEN_MINTUES(60 * 10, "Minuten", "zehn"),
	FIVE_MINTUES(60 * 5, "Minuten", "fuenf"),
	FOUR_MINTUES(60 * 4, "Minuten", "vier"),
	THREE_MINTUES(60 * 3, "Minuten", "drei"),
	TWO_MINTUES(60 * 2, "Minuten", "zwei"),
	MINTUE(60, "Minute", "einer"),
	GO(0, null, null);

	private String article, unit;
	private long delay;
	private boolean used;

	private StartDelay(long delay, String unit, String article) {
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
		StartDelay lastDelay = null;
		for (StartDelay sd : values()) {
			if (!(delay >= sd.getDelay()) || sd.isUsed())
				continue;

			if (lastDelay == null || lastDelay.getDelay() < sd.getDelay())
				lastDelay = sd;
		}

		lastDelay.setUsed(true);
		return lastDelay;
	}

	public static void reset() {
		for (StartDelay sd : values())
			sd.setUsed(false);
	}
}