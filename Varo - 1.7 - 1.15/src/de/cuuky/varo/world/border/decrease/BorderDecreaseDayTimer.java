package de.cuuky.varo.world.border.decrease;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import org.apache.commons.lang.time.DateUtils;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.game.VaroGame;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;
import de.cuuky.varo.world.border.VaroBorder;

public class BorderDecreaseDayTimer implements VaroSerializeable {

	@VaroSerializeField(path = "nextDecrease")
	private Date nextDecrease;

	public BorderDecreaseDayTimer() {}

	public BorderDecreaseDayTimer(boolean new1) {
		if(!ConfigEntry.BORDER_TIME_DAY_DECREASE.getValueAsBoolean() || !VaroGame.getInstance().isRunning())
			return;

		generateNextDecrease();
		startTimer();
		VaroGame.getInstance().setBorderDecrease(this);
	}

	private void generateNextDecrease() {
		nextDecrease = new Date();
		nextDecrease = DateUtils.addDays(nextDecrease, ConfigEntry.BORDER_TIME_DAY_DECREASE_DAYS.getValueAsInt());
	}

	private long getTime() {
		if(nextDecrease.before(new Date()))
			return 20;

		return ((nextDecrease.getTime() - new Date().getTime()) / 1000) * 20;
	}

	private void startTimer() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				if(VaroGame.getInstance().isRunning())
					VaroBorder.getInstance().decreaseBorder(DecreaseReason.TIME_MINUTES);

				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new BukkitRunnable() {

					@Override
					public void run() {
						new BorderDecreaseDayTimer(true);
					}
				}, 20);
			}
		}, getTime());
	}

	@Override
	public void onDeserializeEnd() {
		if(!ConfigEntry.BORDER_TIME_DAY_DECREASE.getValueAsBoolean() || !VaroGame.getInstance().isRunning())
			return;

		if(nextDecrease == null)
			generateNextDecrease();

		startTimer();
	}

	@Override
	public void onSerializeStart() {}
}