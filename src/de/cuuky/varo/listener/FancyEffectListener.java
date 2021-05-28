package de.cuuky.varo.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import de.cuuky.cfw.utils.JavaUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import org.bukkit.scheduler.BukkitRunnable;

public class FancyEffectListener implements Listener {

	private static final Effect[] ALLOWED_EFFECTS;

	static {
		ALLOWED_EFFECTS = new Effect[] { Effect.EXTINGUISH };
	}

	private Map<Entity, Effect> effectEntities;

	public FancyEffectListener() {
		effectEntities = new HashMap<>();

		new BukkitRunnable() {
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

	private boolean isEnabled(Cancellable event) {
		return ConfigSetting.WORLD_ENTITY_TRACER.getValueAsBoolean() && !event.isCancelled();
	}

	private void addEntity(Entity entity) {
		effectEntities.put(entity, ALLOWED_EFFECTS[JavaUtils.randomInt(0, ALLOWED_EFFECTS.length - 1)]);
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