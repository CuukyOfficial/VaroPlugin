package de.cuuky.varo.event.events;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.event.VaroEvent;
import de.cuuky.varo.event.VaroEventType;

public class ExposedVaroEvent extends VaroEvent {

	private int scheduler;
	private PotionEffectType type;

	public ExposedVaroEvent() {
		super(VaroEventType.EXPOSED, Material.REDSTONE, "Laesst die Spieler auffliegen!\n\n1.9+: Gibt allen 'GLOWING'-Effekt\n<1.9: Spawnt alle 10 Sekunden eine Rakete");

		type = PotionEffectType.getByName("GLOWING");
	}

	@Override
	public void onDisable() {
		if (type != null)
			for (Player pl : VersionUtils.getOnlinePlayer())
				pl.removePotionEffect(type);

		Bukkit.getScheduler().cancelTask(scheduler);
		super.onDisable();
	}

	@Override
	public void onEnable() {
		scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				FireworkEffect effect = FireworkEffect.builder().withColor(Color.RED, Color.WHITE).withFade(Color.PURPLE).with(FireworkEffect.Type.BURST).trail(false).flicker(true).build();

				for (VaroPlayer vpl : VaroPlayer.getOnlineAndAlivePlayer()) {
					Player pl = vpl.getPlayer();

					if (type != null)
						pl.getPlayer().addPotionEffect(new PotionEffect(type, 11, 1));
					else {
						Firework fw = (Firework) pl.getWorld().spawnEntity(pl.getLocation(), EntityType.FIREWORK);
						FireworkMeta meta = fw.getFireworkMeta();
						meta.addEffect(effect);
						meta.setPower(1);
						fw.setFireworkMeta(meta);
					}
				}

			}
		}, 10 * 20, 10 * 20);
		super.onEnable();
	}

}
