package de.cuuky.varo.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.listener.helper.cancelable.CancelableType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelable;
import de.varoplugin.cfw.utils.EventUtils;

public class EntityDamageByEntityListener implements Listener {

	@EventHandler(priority = EventPriority.LOW)
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		if(!Main.getVaroGame().hasStarted() && event.getDamager() instanceof Player && ((Player) event.getDamager()).getGameMode() != GameMode.CREATIVE) {
			event.setCancelled(true);
			return;
		}
		
		if (!(event.getEntity() instanceof Player))
			return;

		if (Main.getVaroGame().getGameState() == GameState.END)
			return;

		Player p = (Player) event.getEntity(), damager = EventUtils.getDamager(event);
		VaroPlayer vp = VaroPlayer.getPlayer(p), vDamager = damager != null ? VaroPlayer.getPlayer(damager) : null;
		if (Main.getVaroGame().getProtection() != null) {
			if (damager == null)
				vp.sendMessage(ConfigMessages.PROTECTION_TIME_RUNNING);
			else
				vDamager.sendMessage(ConfigMessages.PROTECTION_TIME_RUNNING, vDamager);
			event.setCancelled(true);
			return;
		}

		if (Main.getVaroGame().getGameState() == GameState.LOBBY || VaroCancelable.getCancelable(vp, CancelableType.PROTECTION) != null || vp.isInProtection()) {
			event.setCancelled(true);
			return;
		}

		if (ConfigSetting.FRIENDLYFIRE.getValueAsBoolean() || damager == null)
			return;

		if (VaroCancelable.getCancelable(vDamager, CancelableType.PROTECTION) != null || vDamager.isInProtection() && !Main.getVaroGame().isFirstTime()) {
			event.setCancelled(true);
			return;
		}

		if (damager.equals(p) || vp.getTeam() == null || vDamager.getTeam() == null || !vp.getTeam().equals(vDamager.getTeam()))
			return;

		event.setCancelled(true);
		vDamager.sendMessage(ConfigMessages.COMBAT_FRIENDLY_FIRE);
		return;
	}
}
