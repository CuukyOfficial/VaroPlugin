package de.cuuky.varo.utils.varo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.utils.IPUtils;

public class OutSideTimeChecker {

	private ZonedDateTime date1, date2;

	public OutSideTimeChecker() {
		refreshDates();
	}

	private void refreshDates() {
		date1 = ZonedDateTime.of(LocalDate.now(), LocalTime.of(ConfigSetting.ONLY_JOIN_BETWEEN_HOURS_HOUR1.getValueAsInt(), ConfigSetting.ONLY_JOIN_BETWEEN_HOURS_MINUTE1.getValueAsInt()), ZoneId.systemDefault());
		date2 = ZonedDateTime.of(LocalDate.now(), LocalTime.of(ConfigSetting.ONLY_JOIN_BETWEEN_HOURS_HOUR2.getValueAsInt(), ConfigSetting.ONLY_JOIN_BETWEEN_HOURS_MINUTE2.getValueAsInt()), ZoneId.systemDefault());

		if (date2.isBefore(date1)) {
			date2 = date2.plusDays(1);
		}
	}

	public boolean canJoin(String IP) {
		if(!ConfigSetting.ONLY_JOIN_BETWEEN_HOURS.getValueAsBoolean())
			return true;

		refreshDates();

		if (!ConfigSetting.ONLY_JOIN_BETWEEN_HOURS_PLAYER_TIME.getValueAsBoolean()) {
			ZonedDateTime now = ZonedDateTime.now();
			if (now.isAfter(date1) && now.isBefore(date2)) {
				return true;
			} else if (now.plusDays(1).isAfter(date1) && now.plusDays(1).isBefore(date2)) {
				return true;
			}
		} else {
			ZonedDateTime cal = IPUtils.ipToTime(IP);
			cal = cal.withZoneSameLocal(ZoneId.systemDefault());
			if (cal.isAfter(date1) && cal.isBefore(date2)) {
				return true;
			} else if (cal.plusDays(1).isAfter(date1) && cal.plusDays(1).isBefore(date2)) {
				return true;
			}
		}

		return false;
	}

	public String[] getTimesForPlayer(String IP) {
		refreshDates();

		date1 = date1.withZoneSameInstant(ZoneId.of(IPUtils.ipToTimezone(IP)));
		date2 = date2.withZoneSameInstant(ZoneId.of(IPUtils.ipToTimezone(IP)));

		String[] dates1 = date1.format(DateTimeFormatter.ofPattern("HH,mm")).split(",");
		String[] dates2 = date2.format(DateTimeFormatter.ofPattern("HH,mm")).split(",");
		String[] dates = {dates1[0], dates1[1], dates2[0], dates2[1]};

		return dates;
	}

	public ZonedDateTime getDate2() {
		return date2;
	}
}