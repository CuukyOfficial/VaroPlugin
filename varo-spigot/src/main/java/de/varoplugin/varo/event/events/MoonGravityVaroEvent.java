package de.varoplugin.varo.event.events;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.cfw.version.VersionUtils;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.event.VaroEvent;
import de.varoplugin.varo.event.VaroEventType;

public class MoonGravityVaroEvent extends VaroEvent {

	private BukkitTask sched;
	private PotionEffectType type;

	public MoonGravityVaroEvent() {
		super(VaroEventType.MOON_GRAVITY, XMaterial.STONE, "Mond-Gravitation\nVorsicht: Ab 1.13 möglich.");

		type = PotionEffectType.getByName("SLOW_FALLING");
	}

	@Override
	public void onDisable() {
		if (type == null)
			return;

		sched.cancel();
		for (Player p : VersionUtils.getVersionAdapter().getOnlinePlayers())
			p.removePotionEffect(PotionEffectType.getByName("SLOW_FALLING"));
	}

	@Override
	public void onEnable() {
		if (type == null) {
			enabled = false;
			return;
		}

		sched = new BukkitRunnable() {
			@Override
			public void run() {
				for (Player p : VersionUtils.getVersionAdapter().getOnlinePlayers())
					p.addPotionEffect(new PotionEffect(PotionEffectType.getByName("SLOW_FALLING"), 9999, 1));
			}
		}.runTaskTimer(Main.getInstance(), 1L, 100L);
	}
}