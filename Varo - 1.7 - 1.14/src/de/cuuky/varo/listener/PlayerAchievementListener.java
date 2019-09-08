package de.cuuky.varo.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

@SuppressWarnings("deprecation")
public class PlayerAchievementListener implements Listener {

	@EventHandler
	public void on(PlayerAchievementAwardedEvent event) {
		event.setCancelled(true);
	}
}
