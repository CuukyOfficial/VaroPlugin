package de.cuuky.varo.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.listener.helper.cancelable.CancelAbleType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelAble;

public class EntityDamageListener implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamage(EntityDamageEvent event) {
		Entity e = event.getEntity();
		if (!(e instanceof Player))
			return;

		if (Main.getVaroGame().getGameState() == GameState.END)
			return;

		Player pl = (Player) e;
		VaroPlayer vp = VaroPlayer.getPlayer(pl);

		if (Main.getVaroGame().getGameState() == GameState.LOBBY || VaroCancelAble.getCancelAble(vp, CancelAbleType.PROTECTION) != null || Main.getVaroGame().getProtection() != null) {
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
