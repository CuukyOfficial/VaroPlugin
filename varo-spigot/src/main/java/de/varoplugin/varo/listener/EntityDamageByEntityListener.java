package de.varoplugin.varo.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.varoplugin.cfw.utils.EventUtils;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.listener.helper.cancelable.CancelableType;
import de.varoplugin.varo.listener.helper.cancelable.VaroCancelable;
import de.varoplugin.varo.player.VaroPlayer;

public class EntityDamageByEntityListener implements Listener {

	@EventHandler(priority = EventPriority.LOW)
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		if(!Main.getVaroGame().hasStarted() && event.getDamager() instanceof Player && ((Player) event.getDamager()).getGameMode() != GameMode.CREATIVE) {
			event.setCancelled(true);
			return;
		}
		
		if (!(event.getEntity() instanceof Player))
			return;

		if (Main.getVaroGame().hasEnded())
			return;

		Player p = (Player) event.getEntity(), damager = EventUtils.getDamager(event);
		VaroPlayer vp = VaroPlayer.getPlayer(p), vDamager = damager != null ? VaroPlayer.getPlayer(damager) : null;
		if (Main.getVaroGame().getProtection() != null) {
			if (damager == null)
			    Messages.PROTECTION_PROTECTED.send(vp);
			else
			    Messages.PROTECTION_PROTECTED.send(vDamager);
			event.setCancelled(true);
			return;
		}

		if (!Main.getVaroGame().hasStarted() || VaroCancelable.getCancelable(vp, CancelableType.PROTECTION) != null || vp.isInProtection()) {
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
		Messages.PLAYER_COMBAT_FRIENDLY_FIRE.send(vDamager);
		return;
	}
}
