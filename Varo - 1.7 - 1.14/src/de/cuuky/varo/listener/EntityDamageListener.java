package de.cuuky.varo.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.Game;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.listener.helper.cancelable.CancelAbleType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelAble;

public class EntityDamageListener implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamage(EntityDamageEvent event) {
		Entity e = event.getEntity();
		if(!(e instanceof Player))
			return;

		if(Game.getInstance().getGameState() == GameState.END)
			return;

		Player pl = (Player) e;
		VaroPlayer vp = VaroPlayer.getPlayer(pl);

		if(Game.getInstance().getGameState() == GameState.LOBBY || VaroCancelAble.getCancelAble(vp, CancelAbleType.PROTECTION) != null || Game.getInstance().getProtection() != null) {
			event.setCancelled(true);
			return;
		}

		if(!ConfigEntry.JOIN_PROTECTIONTIME.isIntActivated() || Game.getInstance().isStarting() || Game.getInstance().isFirstTime())
			return;

		if(vp.isInProtection()) {
			event.setCancelled(true);
			return;
		}
	}
}
