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

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEventType;
import de.cuuky.varo.entity.player.stats.stat.inventory.InventoryBackup;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;
import de.cuuky.varo.version.BukkitVersion;
import de.cuuky.varo.version.VersionUtils;

public class OfflineVillager implements VaroSerializeable {

	private static Class<?> nbttagClass;

	private static ArrayList<OfflineVillager> villagers;

	static {
		villagers = new ArrayList<>();
		Bukkit.getPluginManager().registerEvents(new VillagerListener(), Main.getInstance());

		try {
			nbttagClass = Class.forName(VersionUtils.getNmsClass() + ".NBTTagCompound");
		} catch(Exception | Error e) {
			e.printStackTrace();
		}
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
		if(!VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7)) {
			Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

				@Override
				public void run() {
					zombie.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000, 255));
				}
			}, 0, 1000000 * 20);
		} else
			try {
				Object nmsEn = zombie.getClass().getMethod("getHandle").invoke(zombie);
				Object compound = nbttagClass.newInstance();
				nmsEn.getClass().getMethod("c", compound.getClass()).invoke(nmsEn, compound);
				compound.getClass().getDeclaredMethod("setByte", String.class, byte.class).invoke(compound, "NoAI", (byte) 1);
				nmsEn.getClass().getMethod("f", nbttagClass).invoke(nmsEn, compound);
			} catch(Exception e) {
				e.printStackTrace();
			}
	}

	public void create() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				if(location.getWorld().getDifficulty() == Difficulty.PEACEFUL)
					location.getWorld().setDifficulty(Difficulty.EASY);

				EntityType type = VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_9) ? EntityType.valueOf("ZOMBIE_VILLAGER") : EntityType.ZOMBIE;
				zombie = (Zombie) location.getWorld().spawnEntity(location, type);
				zombie.setCustomName("§c" + vp.getName());
				zombie.setCustomNameVisible(true);

				if(!VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_9))
					zombie.setVillager(true);

				freezeVillager();
				entity = zombie;
			}
		}, 1);
	}
	
	public void kill(VaroPlayer killer) {
		if(zombie != null)
			zombie.getWorld().strikeLightningEffect(zombie.getLocation());

		remove();

		for(ItemStack it : backup.getInventory().getInventory().getContents())
			if(it != null && it.getType() != Material.AIR)
				location.getWorld().dropItemNaturally(location, it);

		for(ItemStack it : backup.getArmor())
			if(it != null && it.getType() != Material.AIR)
				location.getWorld().dropItemNaturally(location, it);

		Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.DEATH, ConfigMessages.ALERT_DISCORD_KILL.getValue().replace("%death%", vp.getName()).replace("%killer%", killer.getName()));
		Bukkit.broadcastMessage(ConfigMessages.DEATH_KILLED_BY.getValue().replace("%death%", vp.getName()).replace("%killer%", killer.getName()));

		killer.onEvent(BukkitEventType.KILL);
		vp.onEvent(BukkitEventType.KILLED);
	}
	
	public void remove() {
		if(zombie != null)
			zombie.remove();

		villagers.remove(this);
	}

	@Override
	public void onDeserializeEnd() {
		this.vp = backup.getVaroPlayer();
		if(vp == null)
			remove();

		for(Entity ent : location.getWorld().getEntities())
			if(ent.getType().toString().contains("ZOMBIE")) {
				Zombie zombie = (Zombie) ent;
				if(zombie.isVillager() && zombie.getCustomName() != null && zombie.getCustomName().equals("§c" + vp.getName())) {
					this.zombie = (Zombie) ent;
					this.entity = ent;
				}
			}

		if(zombie == null)
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
		for(OfflineVillager vill : villagers) {
			if(!entity.equals(vill.getEntity()))
				continue;

			return vill;
		}

		return null;
	}
}