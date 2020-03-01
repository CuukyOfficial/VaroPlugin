package de.cuuky.varo.event.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.event.VaroEvent;
import de.cuuky.varo.utils.BlockUtils;
import de.cuuky.varo.version.VersionUtils;

public class PoisonRainVaroEvent extends VaroEvent {

	private int sched;

	public PoisonRainVaroEvent() {
		super("§4Poisened Rain", Material.ARROW, "Regen macht Schaden");
	}

	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTask(sched);
	}

	@Override
	public void onEnable() {
		sched = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				playerLoop: for(Player p : VersionUtils.getOnlinePlayer()) {
					if(p.getWorld().hasStorm() && !p.getLocation().getBlock().getBiome().toString().contains("SAVANNA")) {
						for(int i = p.getLocation().getBlockY(); i < p.getWorld().getMaxHeight(); i++)
							if(!BlockUtils.isAir(p.getWorld().getBlockAt(p.getLocation().getBlockX(), i, p.getLocation().getBlockZ())))
								continue playerLoop;

						p.damage(0.75);
					}
				}
			}
		}, 1, 20);
	}
}
