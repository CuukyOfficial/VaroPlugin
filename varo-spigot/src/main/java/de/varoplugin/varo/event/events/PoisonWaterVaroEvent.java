package de.varoplugin.varo.event.events;

import org.bukkit.entity.Player;
import de.varoplugin.cfw.scheduler.TaskRunnable;
import de.varoplugin.cfw.scheduler.Task;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.cfw.version.VersionUtils;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.event.VaroEvent;
import de.varoplugin.varo.event.VaroEventType;

public class PoisonWaterVaroEvent extends VaroEvent {

	private Task sched;

	public PoisonWaterVaroEvent() {
		super(VaroEventType.POISON_WATER, XMaterial.WATER_BUCKET, "Bei Kontakt mit Wasser erhält man Schaden");
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
					Main.scheduler().executeFor(p, () -> {
						if (p.getLocation().getBlock().getType().toString().contains("WATER"))
							p.damage(0.75);
					});
			}
		}.runTaskTimer(Main.getInstance(), 1L, 20L);
	}
}