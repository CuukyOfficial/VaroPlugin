package de.cuuky.varo.event.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.cuuky.cfw.utils.BlockUtils;
import de.varoplugin.cfw.version.VersionUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.event.VaroEvent;
import de.cuuky.varo.event.VaroEventType;

public class PoisonRainVaroEvent extends VaroEvent {

	private BukkitTask sched;

	public PoisonRainVaroEvent() {
		super(VaroEventType.POISON_RAIN, Material.ARROW, "Regen macht Schaden");
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
				playerLoop: for (Player p : VersionUtils.getVersionAdapter().getOnlinePlayers()) {
					if (p.getWorld().hasStorm() && !p.getLocation().getBlock().getBiome().toString().contains("SAVANNA")) {
						for (int i = p.getLocation().getBlockY(); i < p.getWorld().getMaxHeight(); i++)
							if (!BlockUtils.isAir(p.getWorld().getBlockAt(p.getLocation().getBlockX(), i, p.getLocation().getBlockZ())))
								continue playerLoop;

						p.damage(0.75);
					}
				}
			}
		}.runTaskTimer(Main.getInstance(), 1L, 20L);
	}
}