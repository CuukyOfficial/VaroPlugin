package de.cuuky.varo.game.world.border.decrease;

import java.util.ArrayList;

import org.bukkit.Bukkit;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.messages.ConfigMessages;
import de.cuuky.varo.game.world.VaroWorldHandler;

public class BorderDecrease {

	private static ArrayList<BorderDecrease> decreases;
	private static boolean running;

	static {
		decreases = new ArrayList<>();
		running = false;

		startShrinking();
	}

	private double amount, bps;
	private Runnable startHook, finishHook;

	public BorderDecrease(double amount, double bps) {
		this.amount = amount;
		this.bps = bps;

		decreases.add(this);
	}

	private void waitForBorder(double d) {
		try {
			Thread.sleep((long) (d * 1000) + 1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void shrink() {
		VaroWorldHandler worldHandler = Main.getVaroGame().getVaroWorldHandler();

		int minsize = ConfigSetting.MIN_BORDER_SIZE.getValueAsInt();
		double size = worldHandler.getBorderSize(null);
		if(size <= minsize) {
			Bukkit.broadcastMessage(ConfigMessages.BORDER_MINIMUM_REACHED.getValue());
			remove();
			return;
		}

		if(startHook != null)
			startHook.run();
		if(minsize > 0)
			if((int) (size - amount) < minsize) {
				worldHandler.setBorderSize(minsize, (long) ((size - minsize) / bps), null);
				waitForBorder((size - minsize) / bps);
			} else {
				worldHandler.setBorderSize(size - amount, (long) (amount / bps), null);
				waitForBorder(amount / bps);
			}

		if(finishHook != null)
			finishHook.run();
		remove();
	}

	public void remove() {
		decreases.remove(this);
	}

	public double getBps() {
		return bps;
	}

	public void setFinishHook(Runnable finishHook) {
		this.finishHook = finishHook;
	}

	public void setStartHook(Runnable startHook) {
		this.startHook = startHook;
	}

	private static void startShrinking() {
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.getInstance(), new Runnable() {

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				if(running)
					return;

				for(int i = 0; i < decreases.size(); i++) {
					running = true;
					decreases.get(i).shrink();
				}

				running = false;
			}
		}, 1, 20);
	}
}