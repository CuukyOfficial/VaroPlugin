package de.cuuky.varo.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.entity.player.VaroPlayer;

public class PlayerRegenerateListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onHealthRegenerate(EntityRegainHealthEvent event) {
		if(!(event.getEntity() instanceof Player))
			return;

		if(event.isCancelled())
			return;

		if(event.getRegainReason() == RegainReason.SATIATED)
			if(ConfigEntry.NO_SATIATION_REGENERATE.getValueAsBoolean()) {
				event.setCancelled(true);
				return;
			}

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				VaroPlayer vp = VaroPlayer.getPlayer((Player) event.getEntity());

				if(vp.getNametag() != null)
					VaroPlayer.getPlayer((Player) event.getEntity()).getNametag().heartsChanged();
			}
		}, 1);
	}
}
