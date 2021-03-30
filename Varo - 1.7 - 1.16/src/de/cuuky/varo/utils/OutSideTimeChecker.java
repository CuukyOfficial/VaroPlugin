package de.cuuky.varo.utils;

import java.util.GregorianCalendar;

import de.cuuky.varo.configuration.configurations.config.ConfigSetting;

public class OutSideTimeChecker {

	private GregorianCalendar date1, date2;

	public OutSideTimeChecker() {
		refreshDates();
	}

	private void refreshDates() {
		this.date1 = new GregorianCalendar();
		date1.set(GregorianCalendar.SECOND, 0);
		this.date2 = (GregorianCalendar) date1.clone();

		date1.set(GregorianCalendar.HOUR_OF_DAY, ConfigSetting.ONLY_JOIN_BETWEEN_HOURS_HOUR1.getValueAsInt());
		date1.set(GregorianCalendar.MINUTE, ConfigSetting.ONLY_JOIN_BETWEEN_HOURS_MINUTE1.getValueAsInt());
		date2.set(GregorianCalendar.HOUR_OF_DAY, ConfigSetting.ONLY_JOIN_BETWEEN_HOURS_HOUR2.getValueAsInt());
		date2.set(GregorianCalendar.MINUTE, ConfigSetting.ONLY_JOIN_BETWEEN_HOURS_MINUTE2.getValueAsInt());

		if (date2.before(date1))
			date2.add(GregorianCalendar.DAY_OF_MONTH, 1);
	}

	public boolean canJoin() {
		if (!ConfigSetting.ONLY_JOIN_BETWEEN_HOURS.getValueAsBoolean())
			return true;

		GregorianCalendar current = new GregorianCalendar();
		refreshDates();
		if (current.after(date1) && current.before(date2))
			return true;

		return false;
	}

	public GregorianCalendar getDate1() {
		return this.date1;
	}

	public GregorianCalendar getDate2() {
		return this.date2;
	}
}