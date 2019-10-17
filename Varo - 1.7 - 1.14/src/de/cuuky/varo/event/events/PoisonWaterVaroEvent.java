package de.cuuky.varo.event.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.event.VaroEvent;
import de.cuuky.varo.version.VersionUtils;

public class PoisonWaterVaroEvent extends VaroEvent {

	private int sched;

	public PoisonWaterVaroEvent() {
		super("§bPoisoned Water", Material.WATER_BUCKET, "Bei Kontakt mit Wasser erhält man Schaden");

	}

	@Override
	public void onEnable() {
		sched = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				for(Player p : VersionUtils.getOnlinePlayer()) {
					if(p.getLocation().getBlock().getType().toString().contains("WATER"))
						p.damage(0.75);
				}
			}
		}, 1, 20);
	}

	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTask(sched);
	}
}
