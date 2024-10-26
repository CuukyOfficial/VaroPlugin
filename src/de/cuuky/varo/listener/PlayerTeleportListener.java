package de.cuuky.varo.listener;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.player.VaroPlayer;

public class PlayerTeleportListener implements Listener {

	@EventHandler
	public void onTeleport(PlayerTeleportEvent event) {
	    VaroPlayer vp = VaroPlayer.getPlayer(event.getPlayer());
		if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)) {
			if (event.getFrom().getWorld().getEnvironment() == World.Environment.NORMAL && event.getTo().getWorld().getEnvironment() == World.Environment.NETHER) {
			    if (!event.getPlayer().isOp() && (!Main.getVaroGame().hasStarted() || (!ConfigSetting.FINALE_ALLOW_NETHER.getValueAsBoolean() && Main.getVaroGame().isFinale()))) {
			        event.setCancelled(true);
			        return;
			    }
			    
				if (event.getFrom().distance(event.getFrom().getWorld().getSpawnLocation()) < 500) {
					event.getTo().getWorld().setSpawnLocation(event.getTo().getBlockX(), event.getTo().getBlockY(), event.getTo().getBlockZ());
				}
			}
		}

		if (!vp.getStats().isSpectator() && !vp.isAdminIgnore() || event.getPlayer().isOp())
			return;

		if (event.getTo().getY() >= ConfigSetting.MINIMAL_SPECTATOR_HEIGHT.getValueAsInt())
			return;

		event.setCancelled(true);
	}
}
