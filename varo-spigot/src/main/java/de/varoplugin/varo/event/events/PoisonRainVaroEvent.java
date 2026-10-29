package de.varoplugin.varo.event.events;

import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.cfw.scheduler.Task;
import de.varoplugin.cfw.scheduler.TaskRunnable;
import de.varoplugin.cfw.version.VersionUtils;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.event.VaroEvent;
import de.varoplugin.varo.event.VaroEventType;
import de.varoplugin.varo.utils.VaroUtils;

public class PoisonRainVaroEvent extends VaroEvent {

	private Task sched;

	public PoisonRainVaroEvent() {
		super(VaroEventType.POISON_RAIN, XMaterial.ARROW, "Regen macht Schaden");
	}

	@Override
	public void onDisable() {
		sched.cancel();
	}

	@Override
	public void onEnable() {
		sched = new TaskRunnable() {
			@Override
			public void run() {
				for (Player p : VersionUtils.getVersionAdapter().getOnlinePlayers())
					Main.scheduler().executeFor(p, () -> damageIfRainedOn(p));
			}

			private void damageIfRainedOn(Player p) {
				if (p.getWorld().hasStorm() && !p.getLocation().getBlock().getBiome().toString().contains("SAVANNA")) {
					for (int i = p.getLocation().getBlockY(); i < p.getWorld().getMaxHeight(); i++)
						if (!VaroUtils.isNotSolidTerrain(p.getWorld().getBlockAt(p.getLocation().getBlockX(), i, p.getLocation().getBlockZ())))
							return;

					p.damage(0.75);
				}
			}
		}.runTaskTimer(Main.getInstance(), 1L, 20L);
	}
}