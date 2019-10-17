package de.cuuky.varo.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.listener.helper.cancelable.CancelAbleType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelAble;
import de.cuuky.varo.player.VaroPlayer;

public class EntityDamageListener implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamage(EntityDamageEvent event) {
		Entity e = event.getEntity();
		if (!(e instanceof Player))
			return;

		if (Main.getGame().getGameState() == GameState.END)
			return;

		Player pl = (Player) e;

		if (Main.getGame().getGameState() == GameState.LOBBY
				|| VaroCancelAble.getCancelAble(pl, CancelAbleType.PROTECTION) != null
				|| Main.getGame().getProtection() != null) {
			event.setCancelled(true);
			return;
		}

		if (!ConfigEntry.JOIN_PROTECTIONTIME.isIntActivated() || Main.getGame().isStarting()
				|| Main.getGame().isFirstTime())
			return;

		VaroPlayer vp = VaroPlayer.getPlayer(pl.getName());
		if (vp.isInProtection()) {
			event.setCancelled(true);
			return;
		}
	}
}
