package de.varoplugin.varo.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;

public class PlayerRegenerateListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onHealthRegenerate(EntityRegainHealthEvent event) {
		if (!(event.getEntity() instanceof Player))
			return;

		if (event.isCancelled())
			return;

		if (event.getRegainReason() == RegainReason.SATIATED)
			if (ConfigSetting.NO_SATIATION_REGENERATE.getValueAsBoolean()) {
				event.setCancelled(true);
				return;
			}
	}
}
