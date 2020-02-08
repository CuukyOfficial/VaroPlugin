package de.cuuky.varo.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.configuration.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.VaroGame;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.listener.helper.cancelable.CancelAbleType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelAble;
import de.cuuky.varo.listener.utils.EntityDamageByEntityUtil;

public class EntityDamageByEntityListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		if(!(event.getEntity() instanceof Player))
			return;

		if(VaroGame.getInstance().getGameState() == GameState.END)
			return;

		Player p = (Player) event.getEntity();
		if(VaroGame.getInstance().getProtection() != null) {
			p.sendMessage(Main.getPrefix() + ConfigMessages.PROTECTION_TIME_RUNNING.getValue());
			event.setCancelled(true);
			return;
		}

		VaroPlayer vp = VaroPlayer.getPlayer(p);
		if(VaroGame.getInstance().getGameState() == GameState.LOBBY || VaroCancelAble.getCancelAble(vp, CancelAbleType.PROTECTION) != null || vp.isInProtection()) {
			event.setCancelled(true);
			return;
		}

		if(ConfigEntry.FRIENDLYFIRE.getValueAsBoolean())
			return;

		Player damager = new EntityDamageByEntityUtil(event).getDamager();
		if(damager == null)
			return;

		VaroPlayer vdamager = VaroPlayer.getPlayer(damager);
		if(VaroCancelAble.getCancelAble(vdamager, CancelAbleType.PROTECTION) != null || vdamager.isInProtection() && !VaroGame.getInstance().isFirstTime()) {
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
