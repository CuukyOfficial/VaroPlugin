package de.varoplugin.varo.player.stats.stat.offlinevillager;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

import de.varoplugin.cfw.scheduler.TaskRunnable;
import de.varoplugin.cfw.scheduler.TaskScheduler;
import de.varoplugin.cfw.version.ServerVersion;
import de.varoplugin.cfw.version.VersionUtils;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.config.language.Contexts;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.logger.logger.EventLogger.LogType;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.player.event.BukkitEventType;
import de.varoplugin.varo.player.stats.stat.inventory.InventoryBackup;
import de.varoplugin.varo.serialize.identifier.VaroSerializeField;
import de.varoplugin.varo.serialize.identifier.VaroSerializeable;
import de.varoplugin.varo.utils.SynchronizedArrayList;

public class OfflineVillager implements VaroSerializeable {

	private static ArrayList<OfflineVillager> villagers;

	static {
		villagers = new SynchronizedArrayList<>();
		Bukkit.getPluginManager().registerEvents(new VillagerListener(), Main.getInstance());
	}

	@VaroSerializeField(path = "lastInventory")
	private InventoryBackup backup;

	@VaroSerializeField(path = "villagerLocation")
	private Location location;

	private VaroPlayer vp;
	private volatile Zombie zombie;
	private volatile Entity entity;

	public OfflineVillager() {
		villagers.add(this);
	}

	public OfflineVillager(VaroPlayer vp, Location location) {
		this.backup = new InventoryBackup(vp);
		this.location = location;
		this.vp = vp;

		create();

		villagers.add(this);
	}

	private void freezeVillager() {
		VersionUtils.getVersionAdapter().removeAi(this.zombie);
	}

	public void create() {
		new TaskRunnable() {
			@Override
			public void run() {
				if (location.getWorld().getDifficulty() == Difficulty.PEACEFUL)
					Main.scheduler().execute(() -> location.getWorld().setDifficulty(Difficulty.EASY));

				EntityType type = VersionUtils.getVersion().isHigherThan(ServerVersion.VERSION_1_9) ? EntityType.valueOf("ZOMBIE_VILLAGER") : EntityType.ZOMBIE;
				zombie = (Zombie) location.getWorld().spawnEntity(location, type);
				zombie.setCustomName("§c" + vp.getName());
				zombie.setCustomNameVisible(true);

				if (!VersionUtils.getVersion().isHigherThan(ServerVersion.VERSION_1_9))
					zombie.setVillager(true);

				freezeVillager();
				entity = zombie;
			}
		}.runTaskAtLater(Main.getInstance(), location, 1L);
	}

	public void kill(VaroPlayer killer, String cause) {
		remove();

		for (ItemStack it : backup.getAllContents())
			if (it != null && it.getType() != Material.AIR)
				location.getWorld().dropItemNaturally(location, it);
		int exp = Math.min(this.backup.getExpLevel() * 7, 100); // See EntityHuman#getExpValue (1.8.8)
		((ExperienceOrb) this.location.getWorld().spawnEntity(this.location, EntityType.EXPERIENCE_ORB)).setExperience(exp);

		Messages.LOG_DEATH_ELIMINATED_PLAYER.log(LogType.KILL, new Contexts.KillContext(this.vp, killer, cause));
		Messages.PLAYER_DEATH_ELIMINATED_PLAYER.broadcast(new Contexts.KillContext(this.vp, killer, cause));

		killer.onEvent(BukkitEventType.KILL);
		this.vp.onEvent(BukkitEventType.DEATH);
		this.vp.onEvent(BukkitEventType.DEATH_NO_LIVES);
	}

	public void remove() {
		final Zombie zombie = this.zombie;
		if (zombie != null)
			Main.scheduler().executeFor(zombie, zombie::remove);

		villagers.remove(this);
	}

	@Override
	public void onDeserializeEnd() {
		this.vp = backup.getVaroPlayer();
		if (vp == null)
			remove();

		// Entities can only be accessed by the thread owning their region on Folia. There only the entities in the villager's chunk are searched.
		Main.scheduler().executeAt(location, () -> {
			TaskScheduler scheduler = Main.scheduler();
			for (Entity ent : scheduler.isRegionized() ? Arrays.asList(location.getChunk().getEntities()) : location.getWorld().getEntities())
				if (ent.getType().toString().contains("ZOMBIE")) {
					Zombie zombie = (Zombie) ent;
					if (zombie.isVillager() && zombie.getCustomName() != null && zombie.getCustomName().equals("§c" + vp.getName())) {
						this.zombie = (Zombie) ent;
						this.entity = ent;
					}
				}

			if (zombie == null)
				create();
		});
	}

	@Override
	public void onSerializeStart() {}

	public Entity getEntity() {
		return entity;
	}

	public VaroPlayer getVp() {
		return vp;
	}

	public Zombie getZombie() {
		return zombie;
	}

	public static OfflineVillager getVillager(Entity entity) {
		for (OfflineVillager vill : villagers) {
			if (!entity.equals(vill.getEntity()))
				continue;

			return vill;
		}

		return null;
	}
}