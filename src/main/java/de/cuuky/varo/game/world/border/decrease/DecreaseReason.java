package de.cuuky.varo.game.world.border.decrease;

import de.cuuky.varo.config.language.Contexts.BorderDecreaseContext;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.config.language.Messages.VaroMessage;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.logger.logger.EventLogger.LogType;

public enum DecreaseReason {

	DEATH(ConfigSetting.BORDER_DEATH_DECREASE, ConfigSetting.BORDER_DEATH_DECREASE_SIZE, ConfigSetting.BORDER_DEATH_DECREASE_SPEED, null, Messages.BORDER_DECREASE_DEATH, Messages.LOG_BORDER_DECREASED_DEATH),
	TIME_DAYS(ConfigSetting.BORDER_TIME_DAY_DECREASE, ConfigSetting.BORDER_TIME_DAY_DECREASE_SIZE, ConfigSetting.BORDER_TIME_DAY_DECREASE_SPEED, ConfigSetting.BORDER_TIME_DAY_DECREASE_DAYS, Messages.BORDER_DECREASE_DAYS, Messages.LOG_BORDER_DECREASED_TIME_DAYS),
	TIME_MINUTES(ConfigSetting.BORDER_TIME_MINUTE_DECREASE, ConfigSetting.BORDER_TIME_MINUTE_DECREASE_SIZE, ConfigSetting.BORDER_TIME_MINUTE_DECREASE_SPEED, ConfigSetting.BORDER_TIME_MINUTE_DECREASE_MINUTES, Messages.BORDER_DECREASE_MINUTES, Messages.LOG_BORDER_DECREASED_TIME_MINUTE);

	private VaroMessage broadcast, log;
	private ConfigSetting enabled, size, speed, time;

	private DecreaseReason(ConfigSetting enabled, ConfigSetting size, ConfigSetting speed, ConfigSetting time, VaroMessage broadcast, VaroMessage log) {
		this.enabled = enabled;
		this.size = size;
		this.speed = speed;
		this.time = time;
		this.broadcast = broadcast;
		this.log = log;
	}

	public double getDecreaseSpeed() {
		try {
			return speed.getValueAsInt();
		} catch (IllegalArgumentException e) {
			return speed.getValueAsDouble();
		}
	}

	public void postLog() {
		this.log.log(LogType.BORDER, new BorderDecreaseContext(this.getSize(), this.getDecreaseSpeed(), this.getTime()));
	}

	public void postBroadcast() {
		this.broadcast.broadcast(new BorderDecreaseContext(this.getSize(), this.getDecreaseSpeed(), this.getTime()));
	}

	public int getSize() {
		return size.getValueAsInt();
	}

	public int getTime() {
		return time == null ? 0 : time.getValueAsInt();
	}

	public boolean isEnabled() {
		return enabled.getValueAsBoolean();
	}
}