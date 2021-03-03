package de.cuuky.varo.game.world.border.decrease;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.logger.logger.EventLogger.LogType;

public enum DecreaseReason {

	DEATH(ConfigSetting.BORDER_DEATH_DECREASE, ConfigSetting.BORDER_DEATH_DECREASE_SIZE, ConfigSetting.BORDER_DEATH_DECREASE_SPEED, null, ConfigMessages.BORDER_DECREASE_DEATH, ConfigMessages.ALERT_BORDER_DECREASED_DEATH),
	TIME_DAYS(ConfigSetting.BORDER_TIME_DAY_DECREASE, ConfigSetting.BORDER_TIME_DAY_DECREASE_SIZE, ConfigSetting.BORDER_TIME_DAY_DECREASE_SPEED, ConfigSetting.BORDER_TIME_DAY_DECREASE_DAYS, ConfigMessages.BORDER_DECREASE_DAYS, ConfigMessages.ALERT_BORDER_DECREASED_TIME_DAYS),
	TIME_MINUTES(ConfigSetting.BORDER_TIME_MINUTE_DECREASE, ConfigSetting.BORDER_TIME_MINUTE_DECREASE_SIZE, ConfigSetting.BORDER_TIME_MINUTE_DECREASE_SPEED, ConfigSetting.BORDER_TIME_MINUTE_DECREASE_MINUTES, ConfigMessages.BORDER_DECREASE_MINUTES, ConfigMessages.ALERT_BORDER_DECREASED_TIME_MINUTE);

	private ConfigMessages broadcast, alert;
	private ConfigSetting enabled, size, speed, time;

	private DecreaseReason(ConfigSetting enabled, ConfigSetting size, ConfigSetting speed, ConfigSetting time, ConfigMessages broadcast, ConfigMessages alert) {
		this.enabled = enabled;
		this.size = size;
		this.speed = speed;
		this.time = time;
		this.broadcast = broadcast;
		this.alert = alert;
	}

	public double getDecreaseSpeed() {
		try {
			return speed.getValueAsInt();
		} catch (IllegalArgumentException e) {
			return speed.getValueAsDouble();
		}
	}

	public void postAlert() {
		Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.BORDER, alert.getValue().replace("%size%", String.valueOf(getSize())).replace("%speed%", String.valueOf(getDecreaseSpeed())).replace("%days%", time != null ? String.valueOf(time.getValueAsInt()) : "").replace("%minutes%", time != null ? String.valueOf(time.getValueAsInt()) : ""));
	}

	public void postBroadcast() {
		Main.getLanguageManager().broadcastMessage(broadcast).replace("%size%", String.valueOf(getSize())).replace("%speed%", String.valueOf(getDecreaseSpeed())).replace("%days%", time != null ? String.valueOf(time.getValueAsInt()) : "").replace("%minutes%", time != null ? String.valueOf(time.getValueAsInt()) : "");
	}

	public int getSize() {
		return size.getValueAsInt();
	}

	public int getTime() {
		return time.getValueAsInt();
	}

	public boolean isEnabled() {
		return enabled.getValueAsBoolean();
	}
}