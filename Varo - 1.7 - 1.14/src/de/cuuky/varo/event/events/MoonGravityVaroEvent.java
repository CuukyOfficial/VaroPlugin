package de.cuuky.varo.event.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.cuuky.varo.Main;
import de.cuuky.varo.event.VaroEvent;
import de.cuuky.varo.version.VersionUtils;
import de.cuuky.varo.version.types.Materials;

public class MoonGravityVaroEvent extends VaroEvent {

	private int sched = -1;
	private PotionEffectType type;

	public MoonGravityVaroEvent() {
		super("§2MoonGravity", Materials.STONE.parseMaterial(), "Mond-Gravitation\nVorsicht: Ab 1.13 möglich.");

		type = PotionEffectType.getByName("SLOW_FALLING");
	}

	@Override
	public void onEnable() {
		if(type == null) {
			enabled = false;
			return;
		}

		sched = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				for(Player p : VersionUtils.getOnlinePlayer())
					p.addPotionEffect(new PotionEffect(PotionEffectType.getByName("SLOW_FALLING"), 9999, 1));
			}
		}, 1, 100);
	}

	@Override
	public void onDisable() {
		if(type == null)
			return;

		Bukkit.getScheduler().cancelTask(sched);

		for(Player p : VersionUtils.getOnlinePlayer())
			p.removePotionEffect(PotionEffectType.getByName("SLOW_FALLING"));
	}

}
