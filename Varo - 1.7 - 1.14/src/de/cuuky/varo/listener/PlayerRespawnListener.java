package de.cuuky.varo.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.player.VaroPlayer;

public class PlayerRespawnListener implements Listener {
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				VaroPlayer.getPlayer(event.getPlayer()).setNormalAttackSpeed();
			}
		}, 20);
	}
}
