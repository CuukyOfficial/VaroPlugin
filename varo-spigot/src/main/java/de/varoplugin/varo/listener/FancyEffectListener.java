package de.varoplugin.varo.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Effect;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import de.varoplugin.cfw.scheduler.TaskRunnable;
import de.varoplugin.cfw.utils.JavaUtils;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;

public class FancyEffectListener implements Listener {

	private static final Effect[] ALLOWED_EFFECTS;

	static {
		ALLOWED_EFFECTS = new Effect[] { Effect.EXTINGUISH };
	}

	private Map<Entity, Effect> effectEntities;

	public FancyEffectListener() {
		effectEntities = new HashMap<>();

		// On regionized servers every entity is tracked by its own task (see addEntity)
		if (!Main.scheduler().isRegionized()) {
			new TaskRunnable() {
				@Override
				public void run() {
					if (effectEntities.isEmpty())
						return;
	
					Set<Entity> iterate = effectEntities.keySet();
					for (int i = effectEntities.size() - 1; i != 0; i--) {
						Entity entity = (Entity) iterate.toArray()[i];
	
						if (entity.isDead() || entity.isOnGround()) {
							effectEntities.remove(entity);
							continue;
						}
	
						entity.getWorld().playEffect(entity.getLocation(), effectEntities.get(entity), 1);
					}
				}
			}.runTaskTimer(Main.getInstance(), 1L, 1L);
		}
	}

	private boolean isEnabled(Cancellable event) {
		return ConfigSetting.WORLD_ENTITY_TRACER.getValueAsBoolean() && !event.isCancelled();
	}

	private void addEntity(Entity entity) {
		Effect effect = ALLOWED_EFFECTS[JavaUtils.randomInt(0, ALLOWED_EFFECTS.length - 1)];

		if (Main.scheduler().isRegionized()) {
			// Entities can only be accessed by the thread owning them
			Main.scheduler().runForTimer(entity, task -> {
				if (entity.isDead() || entity.isOnGround())
					task.cancel();
				else
					entity.getWorld().playEffect(entity.getLocation(), effect, 1);
			}, 1L, 1L);
			return;
		}

		effectEntities.put(entity, effect);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onThrow(ProjectileLaunchEvent event) {
		if (!isEnabled(event))
			return;

		addEntity(event.getEntity());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onThrow(PlayerDropItemEvent event) {
		if (!isEnabled(event))
			return;

		addEntity(event.getItemDrop());
	}
}