package de.cuuky.varo.threads;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.varo.Main;

public class SmartLagDetector implements Runnable {

	private ArrayList<Double> lastTps;
	private long lastPost;
	private boolean ramCleared;

	public SmartLagDetector(JavaPlugin instance) {
		this.lastTps = new ArrayList<>();

		Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new LagCounter(), 100L, 1L);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, this, 20 * 60, 30);
	}

	private void checkPerformance() {
		lastPost++;
		if (lastPost == 30)
			lastPost = 0;
		else
			return;

		double size = 0, summ = 0;
		for (int index = 0; index <= 30; index++) {
			if (index >= lastTps.size())
				break;

			double tps = (double) lastTps.toArray()[index];

			size++;
			summ += tps;
		}

		double tpsUsage = (double) (summ / size);
		if (tpsUsage <= 14)
			warnAdmins("§4The CPU-Performance of the server is running low! §cLags could appear!");

		Runtime r = Runtime.getRuntime();
		double ramUsage = (double) (((double) r.totalMemory() - (double) r.freeMemory()) / (double) r.maxMemory());
		if (ramUsage >= 0.95) {
			if (ramCleared)
				warnAdmins("§4the RAM of the server is fully used and the plugin couldn't manage to clear it!");

			System.gc();
			ramCleared = true;
		} else
			ramCleared = false;
	}

	private void warnAdmins(String message) {
		for (Player player : VersionUtils.getOnlinePlayer()) {
			if (!player.hasPermission("varo.warnperformance"))
				continue;

			player.sendMessage(Main.getPrefix() + message);
			player.sendMessage(Main.getPrefix() + "§cType /performance for more info and help");
		}
	}

	@Override
	public void run() {
		lastTps.add(LagCounter.getTPS());

		checkPerformance();
	}
}