package de.cuuky.varo.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.player.VaroPlayer;

public class HealtLoseListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onHealthLose(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player))
			return;

		if (event.isCancelled())
			return;

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				VaroPlayer vp = VaroPlayer.getPlayer((Player) event.getEntity());

				if (vp.getNametag() != null)
					VaroPlayer.getPlayer((Player) event.getEntity()).getNametag().heartsChanged();
			}
		}, 1);
	}

}
