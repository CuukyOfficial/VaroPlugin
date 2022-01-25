package de.cuuky.varo.event.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.event.VaroEvent;
import de.cuuky.varo.event.VaroEventType;

public class PoisonWaterVaroEvent extends VaroEvent {

	private BukkitTask sched;

	public PoisonWaterVaroEvent() {
		super(VaroEventType.POISON_WATER, Material.WATER_BUCKET, "Bei Kontakt mit Wasser erhaelt man Schaden");
	}

	@Override
	public void onDisable() {
		sched.cancel();
	}

	@Override
	public void onEnable() {
		sched = new BukkitRunnable() {
			@Override
			public void run() {
				for (Player p : VersionUtils.getVersionAdapter().getOnlinePlayers()) {
					if (p.getLocation().getBlock().getType().toString().contains("WATER"))
						p.damage(0.75);
				}
			}
		}.runTaskTimer(Main.getInstance(), 1L, 20L);
	}
}