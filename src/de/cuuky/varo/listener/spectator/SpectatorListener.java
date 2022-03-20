package de.cuuky.varo.listener.spectator;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;

import de.cuuky.cfw.utils.listener.EntityDamageByEntityUtil;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.app.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.vanish.Vanish;

public class SpectatorListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity entityDamager = event.getDamager();
        Entity entityDamaged = event.getEntity();

        if (shouldCancelSpectatorEvent(entityDamaged)) {
            if (entityDamager instanceof Projectile) {
                if (((Projectile) entityDamager).getShooter() instanceof Player) {
                    Projectile projectile = (Projectile) entityDamager;

                    Player shooter = (Player) projectile.getShooter();
                    Player damaged = (Player) entityDamaged;

                    if (Vanish.getVanish((Player) entityDamaged) != null) {
                        damaged.teleport(entityDamaged.getLocation().add(0, 5, 0));

                        Projectile newProjectile = (Projectile) projectile.getWorld().spawnEntity(projectile.getLocation(), projectile.getType());
                        newProjectile.setShooter(shooter);
                        newProjectile.setVelocity(projectile.getVelocity());
                        newProjectile.setBounce(projectile.doesBounce());

                        event.setCancelled(true);
                        projectile.remove();
                    }
                }
            }
        }

        if (!event.isCancelled()) {
            Player damager = new EntityDamageByEntityUtil(event).getDamager();
            if (damager != null)
            	this.checkWorldInteract(event, damager);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
    	if(event.getBlock() == null || event.getBlock().getType() != Materials.FIRE.parseMaterial() || event.getPlayer().getItemInHand() == null
        		|| !(event.getPlayer().getItemInHand().getType() == Materials.FLINT_AND_STEEL.parseMaterial() || event.getPlayer().getItemInHand().getType() == Materials.FIRE_CHARGE.parseMaterial()))
    		this.checkWorldInteract(event, event.getPlayer());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        this.checkWorldInteract(event, event.getPlayer());
    }

    @EventHandler
    public void onProjectile(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player)
            this.checkWorldInteract(event, (Player) event.getEntity().getShooter());
    }

    @EventHandler
    public void onEntityTarget(EntityTargetLivingEntityEvent event) {
        if (Main.getVaroGame().getGameState() == GameState.LOBBY)
            event.setCancelled(true);

        if (shouldCancelSpectatorEvent(event.getTarget())) {
            event.setCancelled(true);
            if (event.getEntity() instanceof ExperienceOrb)
            	event.setTarget(null);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (shouldCancelSpectatorEvent(event.getEntity()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (shouldCancelSpectatorEvent(event.getEntity()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        if (Main.getVaroGame().getGameState() == GameState.LOBBY && !event.getPlayer().isOp())
            event.setCancelled(true);
        else this.checkWorldInteract(event, event.getPlayer());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        VaroPlayer vp = VaroPlayer.getPlayer(event.getPlayer());
        if (!Main.getVaroGame().hasStarted() && event.getPlayer().getGameMode() != GameMode.CREATIVE)
            event.setCancelled(true);
        else if (vp.isInProtection() && (!vp.isAdminIgnore() && vp.getStats().getState() != PlayerState.SPECTATOR))
            event.setCancelled(true);
        else
        	this.checkWorldInteract(event, event.getPlayer());
    }

    @EventHandler
    public void onVehicleDamage(VehicleDamageEvent event) {
    	if (event.getAttacker() instanceof Player) {
    		Player player = (Player) event.getAttacker();
    		if (Main.getVaroGame().getGameState() == GameState.LOBBY && !player.isOp())
                event.setCancelled(true);
            else
            	this.checkWorldInteract(event, player);
    	}
    }

    @EventHandler
    public void onVehicleDamage(VehicleEntityCollisionEvent event) {
    	if (event.getEntity() instanceof Player) {
    		Player player = (Player) event.getEntity();
    		if (Main.getVaroGame().getGameState() == GameState.LOBBY && !player.isOp())
                event.setCancelled(true);
            else
            	this.checkWorldInteract(event, player);
    	}
    }

    @EventHandler
    public void onItemDrop(PlayerPickupItemEvent event) {
        if (Main.getVaroGame().getGameState() == GameState.LOBBY && !event.getPlayer().isOp())
            event.setCancelled(true);
        else this.checkWorldInteract(event, event.getPlayer());
    }

    @EventHandler
    public void onItemPickup(PlayerDropItemEvent event) {
        if (Main.getVaroGame().getGameState() == GameState.LOBBY && !event.getPlayer().isOp())
            event.setCancelled(true);
        else this.checkWorldInteract(event, event.getPlayer());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!event.getPlayer().isOp() && !event.getPlayer().hasPermission("varo.ignorespectatorheight"))
            if (shouldCancelSpectatorEvent(event.getPlayer()))
                if (event.getTo().getY() < ConfigSetting.MINIMAL_SPECTATOR_HEIGHT.getValueAsInt()) {
                    Location tp = event.getFrom();
                    tp.setY(ConfigSetting.MINIMAL_SPECTATOR_HEIGHT.getValueAsInt());
                    event.setTo(tp);
                    VaroPlayer vp = VaroPlayer.getPlayer(event.getPlayer());
                    vp.sendMessage(ConfigMessages.NOPERMISSION_NO_LOWER_FLIGHT, vp);
                }
    }

    private static boolean shouldCancelSpectatorEvent(Entity interact) {
        if (!(interact instanceof Player) || !Main.getVaroGame().hasStarted())
            return false;

        Player player = (Player) interact;
        
        return player.getGameMode() != GameMode.SURVIVAL || VaroPlayer.getPlayer(player).getStats().isSpectator();
    }
    
    private void checkWorldInteract(Cancellable event, Player player) {
        if (!event.isCancelled() && shouldCancelSpectatorEvent(player) && !player.isOp())
        	event.setCancelled(true);
    }
}