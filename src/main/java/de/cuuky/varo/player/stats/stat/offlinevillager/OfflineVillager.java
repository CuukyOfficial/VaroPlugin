package de.cuuky.varo.player.stats.stat.offlinevillager;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.language.Contexts;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.event.BukkitEventType;
import de.cuuky.varo.player.stats.stat.inventory.InventoryBackup;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;
import de.varoplugin.cfw.version.ServerVersion;
import de.varoplugin.cfw.version.VersionUtils;

public class OfflineVillager implements VaroSerializeable {

	private static ArrayList<OfflineVillager> villagers;

	static {
		villagers = new ArrayList<>();
		Bukkit.getPluginManager().registerEvents(new VillagerListener(), Main.getInstance());
	}

	@VaroSerializeField(path = "lastInventory")
	private InventoryBackup backup;

	@VaroSerializeField(path = "villagerLocation")
	private Location location;

	private VaroPlayer vp;
	private Zombie zombie;
	private Entity entity;

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
		new BukkitRunnable() {
			@Override
			public void run() {
				if (location.getWorld().getDifficulty() == Difficulty.PEACEFUL)
					location.getWorld().setDifficulty(Difficulty.EASY);

				EntityType type = VersionUtils.getVersion().isHigherThan(ServerVersion.ONE_9) ? EntityType.valueOf("ZOMBIE_VILLAGER") : EntityType.ZOMBIE;
				zombie = (Zombie) location.getWorld().spawnEntity(location, type);
				zombie.setCustomName("§c" + vp.getName());
				zombie.setCustomNameVisible(true);

				if (!VersionUtils.getVersion().isHigherThan(ServerVersion.ONE_9))
					zombie.setVillager(true);

				freezeVillager();
				entity = zombie;
			}
		}.runTaskLater(Main.getInstance(), 1L);
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
		this.vp.onEvent(BukkitEventType.DEATH_NO_LIFES);
	}

	public void remove() {
		if (zombie != null)
			zombie.remove();

		villagers.remove(this);
	}

	@Override
	public void onDeserializeEnd() {
		this.vp = backup.getVaroPlayer();
		if (vp == null)
			remove();

		for (Entity ent : location.getWorld().getEntities())
			if (ent.getType().toString().contains("ZOMBIE")) {
				Zombie zombie = (Zombie) ent;
				if (zombie.isVillager() && zombie.getCustomName() != null && zombie.getCustomName().equals("§c" + vp.getName())) {
					this.zombie = (Zombie) ent;
					this.entity = ent;
				}
			}

		if (zombie == null)
			create();
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