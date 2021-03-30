package de.cuuky.varo.listener;

import java.util.HashMap;
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

public class FancyEffectListener implements Listener {

	private static final Effect[] ALLOWED_EFFECTS;

	static {
		ALLOWED_EFFECTS = new Effect[] { Effect.EXTINGUISH };
	}

	private HashMap<Entity, Effect> effectEntites;

	public FancyEffectListener() {
		effectEntites = new HashMap<Entity, Effect>();

		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				if (effectEntites.isEmpty())
					return;

				Set<Entity> iterate = effectEntites.keySet();
				for (int i = effectEntites.size() - 1; i != 0; i--) {
					Entity entity = (Entity) iterate.toArray()[i];

					if (entity.isDead() || entity.isOnGround()) {
						effectEntites.remove(entity);
						continue;
					}

					entity.getWorld().playEffect(entity.getLocation(), effectEntites.get(entity), 1);
				}
			}
		}, 1, 1);
	}

	private boolean isEnabled(Cancellable event) {
		return ConfigSetting.WORLD_ENTITY_TRACER.getValueAsBoolean() && !event.isCancelled();
	}

	private void addEntity(Entity entity) {
		effectEntites.put(entity, ALLOWED_EFFECTS[JavaUtils.randomInt(0, ALLOWED_EFFECTS.length - 1)]);
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