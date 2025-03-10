package de.cuuky.varo.player.stats.stat.offlinevillager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.player.VaroPlayer;
import de.varoplugin.cfw.utils.EventUtils;

public class VillagerListener implements Listener {

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if (!event.getEntity().getType().toString().contains("ZOMBIE") || event.getCause().toString().contains("ENTITY"))
			return;

		OfflineVillager vill = OfflineVillager.getVillager(event.getEntity());
		if (vill == null)
			return;

		event.setCancelled(true);
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (!event.getEntity().getType().toString().contains("ZOMBIE"))
			return;

		OfflineVillager vill = OfflineVillager.getVillager(event.getEntity());
		if (vill == null)
			return;

		Player damager = EventUtils.getDamager(event);
		if (damager == null)
			return;

		VaroPlayer vp = VaroPlayer.getPlayer(damager);
		if (Main.getVaroGame().getProtection() != null) {
			vp.sendMessage(ConfigMessages.PROTECTION_TIME_RUNNING);
			event.setCancelled(true);
			return;
		}
		
		if (vp.getTeam() == null || vill.getVp().getTeam() == null || !vp.getTeam().equals(vill.getVp().getTeam()))
			return;

		event.setCancelled(true);
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if (event.getEntity().getKiller() == null || !event.getEntity().getType().toString().contains("ZOMBIE"))
			return;

		OfflineVillager vill = OfflineVillager.getVillager(event.getEntity());
		if (vill == null)
			return;

		event.setDroppedExp(0);
		event.getDrops().clear();
		vill.kill(VaroPlayer.getPlayer(event.getEntity().getKiller()));
	}

	@EventHandler
	public void onInteract(PlayerInteractEntityEvent event) {
		if (!event.getRightClicked().getType().toString().contains("ZOMBIE"))
			return;

		OfflineVillager vill = OfflineVillager.getVillager(event.getRightClicked());
		if (vill == null)
			return;

		event.setCancelled(true);
	}
}