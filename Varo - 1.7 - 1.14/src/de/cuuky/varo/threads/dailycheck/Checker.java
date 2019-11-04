package de.cuuky.varo.threads.dailycheck;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public abstract class Checker {

	private static ArrayList<Checker> checker;

	static {
		checker = new ArrayList<Checker>();

		new NoJoinCheck();
		new BloodLustCheck();
		new SessionCheck();
		new YouTubeCheck();
		new CoordsCheck();
	}

	public Checker() {
		checker.add(this);
	}

	protected long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}
	
	public abstract void check();

	public static void checkAll() {
		for(Checker checkers : checker) {
			try {
				checkers.check();
			} catch(Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	public static ArrayList<Checker> getChecker() {
		return checker;
	}
}
