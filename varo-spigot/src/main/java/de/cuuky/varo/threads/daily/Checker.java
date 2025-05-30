package de.cuuky.varo.threads.daily;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public abstract class Checker {

	protected long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}

	public abstract void check();
}
