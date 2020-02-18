package de.cuuky.varo.listener.spectator;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.configuration.messages.ConfigMessages;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.vanish.Vanish;

@SuppressWarnings("deprecation")
public class SpectatorListener implements Listener {

	static {
		Bukkit.getPluginManager().registerEvents(new EntityMountListener(), Main.getInstance());
	}

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		Entity entityDamager = event.getDamager();
		Entity entityDamaged = event.getEntity();

		if(cancelEvent(event.getEntity())) {
			if(entityDamager instanceof Arrow) {
				if(((Arrow) entityDamager).getShooter() instanceof Player) {
					Arrow arrow = (Arrow) entityDamager;

					Player shooter = (Player) arrow.getShooter();
					Player damaged = (Player) entityDamaged;

					if(Vanish.getVanish((Player) entityDamaged) != null) {
						damaged.teleport(entityDamaged.getLocation().add(0, 5, 0));

						Arrow newArrow = (Arrow) arrow.getWorld().spawnEntity(arrow.getLocation(), EntityType.ARROW);
						newArrow.setShooter(shooter);
						newArrow.setVelocity(arrow.getVelocity());
						newArrow.setBounce(arrow.doesBounce());

						event.setCancelled(true);
						arrow.remove();
					}
				}
			}
		}

		if(cancelEvent(entityDamager))
			event.setCancelled(true);
	}

	@EventHandler
	public void onEntityTarget(EntityTargetLivingEntityEvent event) {
		if(Main.getVaroGame().getGameState() == GameState.LOBBY)
			event.setCancelled(true);

		if(cancelEvent(event.getTarget()))
			event.setCancelled(true);
	}

	@EventHandler
	public void onFoodLose(FoodLevelChangeEvent event) {
		if(cancelEvent(event.getEntity()))
			event.setCancelled(true);
	}

	@EventHandler
	public void onHealthLose(EntityDamageEvent event) {
		if(cancelEvent(event.getEntity()))
			event.setCancelled(true);
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(Main.getVaroGame().getGameState() == GameState.LOBBY && !event.getPlayer().isOp())
			event.setCancelled(true);

		if(cancelEvent(event.getPlayer()))
			event.setCancelled(true);
	}

	@EventHandler
	public void onInventoryMove(InventoryDragEvent event) {
		if(cancelEvent(event.getWhoClicked()))
			event.setCancelled(true);
	}

	@EventHandler
	public void onItemDrop(PlayerPickupItemEvent event) {
		if(Main.getVaroGame().getGameState() == GameState.LOBBY && !event.getPlayer().isOp())
			event.setCancelled(true);

		if(cancelEvent(event.getPlayer()))
			event.setCancelled(true);
	}

	@EventHandler
	public void onItemPickup(PlayerDropItemEvent event) {
		if(Main.getVaroGame().getGameState() == GameState.LOBBY && !event.getPlayer().isOp())
			event.setCancelled(true);

		if(cancelEvent(event.getPlayer()))
			event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if(!event.getPlayer().isOp())
			if(cancelEvent(event.getPlayer()))
				if(event.getTo().getY() < ConfigEntry.MINIMAL_SPECTATOR_HEIGHT.getValueAsInt()) {
					Location tp = event.getFrom();
					tp.setY(ConfigEntry.MINIMAL_SPECTATOR_HEIGHT.getValueAsInt());
					event.setTo(tp);
					event.getPlayer().sendMessage(Main.getPrefix() + ConfigMessages.WORLD_NO_LOWER_FLIGHT.getValue());
				}
		return;

	}

	private static boolean cancelEvent(Entity interact) {
		if(!(interact instanceof Player))
			return false;

		Player player = (Player) interact;

		if(Vanish.getVanish(player) == null || player.getGameMode() != GameMode.ADVENTURE)
			return false;

		return true;
	}
}