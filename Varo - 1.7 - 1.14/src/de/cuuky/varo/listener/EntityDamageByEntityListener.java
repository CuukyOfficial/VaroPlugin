package de.cuuky.varo.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.listener.helper.cancelable.CancelAbleType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelAble;
import de.cuuky.varo.listener.utils.EntityDamageByEntityUtil;

public class EntityDamageByEntityListener implements Listener {

	// private HashMap<Player, Date> axeHits = new HashMap<>();

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		if(!(event.getEntity() instanceof Player))
			return;

		if(Main.getGame().getGameState() == GameState.END)
			return;

		Player p = (Player) event.getEntity();
		if(Main.getGame().getProtection() != null) {
			p.sendMessage(Main.getPrefix() + ConfigMessages.PROTECTION_TIME_RUNNING.getValue());
			event.setCancelled(true);
			return;
		}

		VaroPlayer vp = VaroPlayer.getPlayer(p);
		if(Main.getGame().getGameState() == GameState.LOBBY || VaroCancelAble.getCancelAble(p, CancelAbleType.PROTECTION) != null || vp.isInProtection()) {
			event.setCancelled(true);
			return;
		}

		// if(p.getItemInHand() != null &&
		// p.getItemInHand().getType().toString().contains("AXE")) {
		// Date curr = new Date();
		// if(axeHits.get(p) != null && ((axeHits.get(p).getTime() -
		// curr.getTime()) * 1000) <= ConfigE) {
		//
		// } else
		// axeHits.put(p, curr);
		// }

		if(ConfigEntry.FRIENDLYFIRE.getValueAsBoolean())
			return;

		Player damager = new EntityDamageByEntityUtil(event).getDamager();
		if(damager == null)
			return;

		VaroPlayer vdamager = VaroPlayer.getPlayer(damager);
		if(VaroCancelAble.getCancelAble(vdamager.getPlayer(), CancelAbleType.PROTECTION) != null || vdamager.isInProtection() && !Main.getGame().isFirstTime()) {
			event.setCancelled(true);
			return;
		}

		if(damager.equals(p) || vp.getTeam() == null || vdamager.getTeam() == null || !vp.getTeam().equals(vdamager.getTeam()))
			return;

		event.setCancelled(true);
		damager.sendMessage(ConfigMessages.COMBAT_FRIENDLY_FIRE.getValue());
		return;
	}
}
