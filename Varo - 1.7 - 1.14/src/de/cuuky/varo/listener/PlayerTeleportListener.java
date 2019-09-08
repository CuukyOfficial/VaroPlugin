package de.cuuky.varo.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.player.VaroPlayer;

public class PlayerTeleportListener implements Listener {

	@EventHandler
	public void onTeleport(PlayerTeleportEvent event) {
		VaroPlayer vp = VaroPlayer.getPlayer(event.getPlayer());
		if(!vp.getStats().isSpectator() && !vp.isAdminIgnore() || event.getPlayer().isOp())
			return;

		if(event.getTo().getY() >= ConfigEntry.MINIMAL_SPECTATOR_HEIGHT.getValueAsInt())
			return;

		event.setCancelled(true);
	}
}
