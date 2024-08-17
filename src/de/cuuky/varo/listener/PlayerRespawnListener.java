package de.cuuky.varo.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;

public class PlayerRespawnListener implements Listener {

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
	    VaroPlayer player = VaroPlayer.getPlayer(event.getPlayer());
	    if (ConfigSetting.PLAYER_SHOW_DEATH_SCREEN.getValueAsBoolean() && player.getStats().getState() == PlayerState.SPECTATOR) {
	        player.setSpectacting();
	        event.setRespawnLocation(event.getPlayer().getLocation());
	    }
	    
		new BukkitRunnable() {
			@Override
			public void run() {
				player.setNormalAttackSpeed();
			}
		}.runTaskLater(Main.getInstance(), 20L);
	}
}
