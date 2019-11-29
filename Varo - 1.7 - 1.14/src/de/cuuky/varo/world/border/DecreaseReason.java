package de.cuuky.varo.world.border;

import de.cuuky.varo.logger.LoggerMaster;
import org.bukkit.Bukkit;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.logger.logger.EventLogger.LogType;

public enum DecreaseReason {

	TIME_DAYS(ConfigEntry.BORDER_TIME_DAY_DECREASE, ConfigEntry.BORDER_TIME_DAY_DECREASE_SIZE, ConfigEntry.BORDER_TIME_DAY_DECREASE_SPEED, ConfigEntry.BORDER_TIME_DAY_DECREASE_DAYS, ConfigMessages.BORDER_DECREASE_DAYS, ConfigMessages.ALERT_BORDER_DECREASED_TIME_DAYS),
	TIME_MINUTES(ConfigEntry.BORDER_TIME_MINUTE_DECREASE, ConfigEntry.BORDER_TIME_MINUTE_DECREASE_SIZE, ConfigEntry.BORDER_TIME_MINUTE_DECREASE_SPEED, ConfigEntry.BORDER_TIME_MINUTE_DECREASE_MINUTES, ConfigMessages.BORDER_DECREASE_MINUTES, ConfigMessages.ALERT_BORDER_DECREASED_TIME_MINUTE),
	DEATH(ConfigEntry.BORDER_DEATH_DECREASE, ConfigEntry.BORDER_DEATH_DECREASE_SIZE, ConfigEntry.BORDER_DEATH_DECREASE_SPEED, null, ConfigMessages.BORDER_DECREASE_DEATH, ConfigMessages.ALERT_BORDER_DECREASED_DEATH);

	private ConfigEntry enabled;
	private ConfigEntry size;
	private ConfigEntry speed;
	private ConfigEntry time;

	private ConfigMessages broadcast;
	private ConfigMessages alert;

	private DecreaseReason(ConfigEntry enabled, ConfigEntry size, ConfigEntry speed, ConfigEntry time, ConfigMessages broadcast, ConfigMessages alert) {
		this.enabled = enabled;
		this.size = size;
		this.speed = speed;
		this.time = time;
		this.broadcast = broadcast;
		this.alert = alert;
	}

	public boolean isEnabled() {
		return enabled.getValueAsBoolean();
	}

	public int getSize() {
		return size.getValueAsInt();
	}

	public int getTime() {
		return time.getValueAsInt();
	}

	public double getDecreaseSpeed() {
		try {
			return speed.getValueAsInt();
		} catch(IllegalArgumentException e) {
			return speed.getValueAsDouble();
		}
	}

	public void postBroadcast() {
		Bukkit.broadcastMessage(broadcast.getValue().replace("%size%", String.valueOf(getSize())).replace("%speed%", String.valueOf(getDecreaseSpeed())).replace("%days%", time != null ? String.valueOf(time.getValueAsInt()) : "").replace("%minutes%", time != null ? String.valueOf(time.getValueAsInt()) : ""));
	}

	public void postAlert() {
		LoggerMaster.getInstance().getEventLogger().println(LogType.BORDER, alert.getValue().replace("%size%", String.valueOf(getSize())).replace("%speed%", String.valueOf(getDecreaseSpeed())).replace("%days%", time != null ? String.valueOf(time.getValueAsInt()) : "").replace("%minutes%", time != null ? String.valueOf(time.getValueAsInt()) : ""));
	}
}
