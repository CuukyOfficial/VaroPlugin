package de.cuuky.varo.entity.player.stats.stat.offlinevillager;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.cuuky.cfw.version.BukkitVersion;
import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEventType;
import de.cuuky.varo.entity.player.stats.stat.inventory.InventoryBackup;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;
import org.bukkit.scheduler.BukkitRunnable;

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

				EntityType type = VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_9) ? EntityType.valueOf("ZOMBIE_VILLAGER") : EntityType.ZOMBIE;
				zombie = (Zombie) location.getWorld().spawnEntity(location, type);
				zombie.setCustomName("§c" + vp.getName());
				zombie.setCustomNameVisible(true);

				if (!VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_9))
					zombie.setVillager(true);

				freezeVillager();
				entity = zombie;
			}
		}.runTaskLater(Main.getInstance(), 1L);
	}

	public void kill(VaroPlayer killer) {
		if (zombie != null)
			zombie.getWorld().strikeLightningEffect(zombie.getLocation());

		remove();

		for (ItemStack it : backup.getInventory().getInventory().getContents())
			if (it != null && it.getType() != Material.AIR)
				location.getWorld().dropItemNaturally(location, it);

		for (ItemStack it : backup.getArmor())
			if (it != null && it.getType() != Material.AIR)
				location.getWorld().dropItemNaturally(location, it);

		Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.DEATH, ConfigMessages.ALERT_DISCORD_KILL.getValue(vp).replace("%death%", vp.getName()).replace("%killer%", killer.getName()));
		Main.getLanguageManager().broadcastMessage(ConfigMessages.DEATH_KILLED_BY, vp).replace("%death%", vp.getName()).replace("%killer%", killer.getName());

		killer.onEvent(BukkitEventType.KILL);
		vp.onEvent(BukkitEventType.KILLED);
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