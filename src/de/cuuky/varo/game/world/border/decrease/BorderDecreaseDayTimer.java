package de.cuuky.varo.game.world.border.decrease;

import de.cuuky.cfw.configuration.serialization.BasicSerializable;
import de.cuuky.cfw.configuration.serialization.Serialize;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;
import org.apache.commons.lang.time.DateUtils;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;

public class BorderDecreaseDayTimer extends BasicSerializable {

	@Serialize("nextDecrease")
	private Date nextDecrease;

	public BorderDecreaseDayTimer(boolean new1) {
		if (!ConfigSetting.BORDER_TIME_DAY_DECREASE.getValueAsBoolean() || !Main.getVaroGame().isRunning())
			return;

		generateNextDecrease();
		startTimer();
		Main.getVaroGame().setBorderDecrease(this);
	}

	private void generateNextDecrease() {
		nextDecrease = new Date();
		nextDecrease = DateUtils.addDays(nextDecrease, ConfigSetting.BORDER_TIME_DAY_DECREASE_DAYS.getValueAsInt());
	}

	private long getTime() {
		if (nextDecrease.before(new Date()))
			return 20;

		return ((nextDecrease.getTime() - new Date().getTime()) / 1000) * 20;
	}

	private void startTimer() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (Main.getVaroGame().isRunning())
					Main.getVaroGame().getVaroWorldHandler().decreaseBorder(DecreaseReason.TIME_MINUTES);

				new BukkitRunnable() {
					@Override
					public void run() {
						new BorderDecreaseDayTimer(true);
					}
				}.runTaskLater(Main.getInstance(), 20L);
			}
		}.runTaskLater(Main.getInstance(), getTime());
	}

	@Override
	public void onDeserializeEnd() {
		if (!ConfigSetting.BORDER_TIME_DAY_DECREASE.getValueAsBoolean() || !Main.getVaroGame().isRunning())
			return;

		if (nextDecrease == null)
			generateNextDecrease();

		startTimer();
	}
}