package de.cuuky.varo.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

import de.cuuky.varo.configuration.configurations.config.ConfigSetting;

@SuppressWarnings("deprecation")
public class PlayerAchievementListener implements Listener {

	@EventHandler
	public void on(PlayerAchievementAwardedEvent event) {
		if(ConfigSetting.BLOCK_ACHIEVEMENTS.getValueAsBoolean()) {
			event.setCancelled(true);	
		}
	}
}
