package de.cuuky.varo.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.listener.helper.cancelable.CancelableType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelable;
import de.cuuky.varo.player.VaroPlayer;

public class EntityDamageListener implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamage(EntityDamageEvent event) {
		Entity e = event.getEntity();
		if (!(e instanceof Player))
			return;

		if (Main.getVaroGame().hasEnded())
			return;

		Player pl = (Player) e;
		VaroPlayer vp = VaroPlayer.getPlayer(pl);

		if (!Main.getVaroGame().hasStarted() || VaroCancelable.getCancelable(vp, CancelableType.PROTECTION) != null || Main.getVaroGame().getProtection() != null) {
			event.setCancelled(true);
			return;
		}

		if (!ConfigSetting.JOIN_PROTECTIONTIME.isIntActivated() || Main.getVaroGame().isStarting() || Main.getVaroGame().isFirstTime())
			return;

		if (vp.isInProtection()) {
			event.setCancelled(true);
			return;
		}
	}
}
