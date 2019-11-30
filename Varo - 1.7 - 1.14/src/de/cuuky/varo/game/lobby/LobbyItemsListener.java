package de.cuuky.varo.game.lobby;

import de.cuuky.varo.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.game.state.GameState;

public class LobbyItemsListener implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(event.getItem() == null || Game.getInstance().getGameState() != GameState.LOBBY)
			return;

		LobbyItem item = LobbyItem.getLobbyItem(event.getItem());
		if(item == null)
			return;

		event.setCancelled(true);
		item.onInteract(event);
	}

	@EventHandler
	public void onInteractEntity(PlayerInteractEntityEvent event) {
		if(event.getPlayer().getItemInHand() == null || event.getRightClicked() == null || Game.getInstance().getGameState() != GameState.LOBBY)
			return;

		LobbyItem item = LobbyItem.getLobbyItem(event.getPlayer().getItemInHand());
		if(item == null)
			return;

		event.setCancelled(true);
		item.onInteractEntity(event);
	}

	@EventHandler
	public void onEntityHit(EntityDamageByEntityEvent event) {
		if(!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player) || ((Player) event.getDamager()).getItemInHand() == null || Game.getInstance().getGameState() != GameState.LOBBY)
			return;

		Player damager = (Player) event.getDamager();
		LobbyItem item = LobbyItem.getLobbyItem(damager.getItemInHand());
		if(item == null)
			return;

		event.setCancelled(true);
		damager.getItemInHand().setDurability((short) 0);
		damager.updateInventory();
		item.onEntityHit(event);
	}
}
