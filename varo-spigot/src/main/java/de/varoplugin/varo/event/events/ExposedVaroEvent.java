package de.varoplugin.varo.event.events;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.cryptomorin.xseries.XEntityType;
import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.cfw.version.VersionUtils;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.event.VaroEvent;
import de.varoplugin.varo.event.VaroEventType;
import de.varoplugin.varo.player.VaroPlayer;

public class ExposedVaroEvent extends VaroEvent {

	private BukkitTask scheduler;
	private PotionEffectType type;

	public ExposedVaroEvent() {
		super(VaroEventType.EXPOSED, XMaterial.REDSTONE, "Lässt die Spieler auffliegen!\n\n1.9+: Gibt allen 'GLOWING'-Effekt\n1.7-1.8: Spawnt alle 10 Sekunden eine Rakete");

		type = PotionEffectType.getByName("GLOWING");
	}

	@Override
	public void onDisable() {
		if (type != null)
			for (Player pl : VersionUtils.getVersionAdapter().getOnlinePlayers())
				pl.removePotionEffect(type);

		scheduler.cancel();
		super.onDisable();
	}

	@Override
	public void onEnable() {
		scheduler = new BukkitRunnable() {
			@Override
			public void run() {
				FireworkEffect effect = FireworkEffect.builder().withColor(Color.RED, Color.WHITE).withFade(Color.PURPLE).with(FireworkEffect.Type.BURST).trail(false).flicker(true).build();

				for (VaroPlayer vpl : VaroPlayer.getOnlineAndAlivePlayer()) {
					Player pl = vpl.getPlayer();

					if (type != null)
						pl.getPlayer().addPotionEffect(new PotionEffect(type, 11, 1));
					else {
						Firework fw = (Firework) pl.getWorld().spawnEntity(pl.getLocation(), XEntityType.FIREWORK_ROCKET.get());
						FireworkMeta meta = fw.getFireworkMeta();
						meta.addEffect(effect);
						meta.setPower(1);
						fw.setFireworkMeta(meta);
					}
				}
			}
		}.runTaskTimer(Main.getInstance(), 10 * 20, 10 * 20);
		super.onEnable();
	}
}