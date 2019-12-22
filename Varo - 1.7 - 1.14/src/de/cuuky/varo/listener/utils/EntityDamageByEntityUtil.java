package de.cuuky.varo.listener.utils;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityUtil {

	private EntityDamageByEntityEvent event;

	public EntityDamageByEntityUtil(EntityDamageByEntityEvent event) {
		this.event = event;
	}

	public Player getDamager() {
		if (event.getDamager() instanceof Arrow) {
			if (!(((Arrow) event.getDamager()).getShooter() instanceof Player))
				return null;

			return ((Player) ((Arrow) event.getDamager()).getShooter());
		} else if (event.getDamager() instanceof Player)
			return (Player) event.getDamager();
		else
			return null;
	}
}