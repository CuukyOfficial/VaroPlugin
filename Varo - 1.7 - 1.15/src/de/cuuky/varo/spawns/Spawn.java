package de.cuuky.varo.spawns;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.configuration.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;
import de.cuuky.varo.spawns.spawn.SpawnType;
import de.cuuky.varo.version.BukkitVersion;
import de.cuuky.varo.version.VersionUtils;
import de.cuuky.varo.version.types.Materials;

public class Spawn implements VaroSerializeable {

	private static ArrayList<Spawn> spawns;

	static {
		spawns = new ArrayList<>();
	}

	@VaroSerializeField(path = "location")
	private Location location;

	@VaroSerializeField(path = "nameTagLocation")
	private Location nameTagLocation;

	@VaroSerializeField(path = "nameTagName")
	private String nameTagName;

	@VaroSerializeField(path = "number")
	private int number;

	@VaroSerializeField(path = "playerId")
	private int playerId;

	@VaroSerializeField(path = "type")
	private SpawnType type;

	private Entity armorStand;
	private VaroPlayer player;

	public Spawn() {
		spawns.add(this);
	}

	public Spawn(SpawnType type, int number, Location location) {
		if(getSpawn(number) != null)
			getSpawn(number).remove();

		this.type = type;
		this.number = number;
		this.location = location;

		setNameTag();

		spawns.add(this);
	}

	public Spawn(SpawnType type, Location location) {
		this.type = type;
		this.number = generateId();
		this.location = location;

		setNameTag();

		spawns.add(this);
	}

	public Spawn(SpawnType type, VaroPlayer player, Location location) {
		this.type = type;
		this.number = generateId();
		this.location = location;
		this.player = player;

		setNameTag();

		spawns.add(this);
	}

	private int generateId() {
		int i = spawns.size() + 1;
		while(getSpawn(i) != null)
			i++;

		return i;
	}

	private void remove() {
		removeNameTag();
		spawns.remove(this);
	}

	private void removeNameTag() {
		if(armorStand == null)
			return;

		armorStand.remove();
	}

	private void setNameTag() {
		if(!ConfigEntry.SET_NAMETAGS_OVER_SPAWN.getValueAsBoolean() || !VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7))
			return;

		nameTagLocation = location.clone().add(0, ConfigEntry.NAMETAG_SPAWN_HEIGHT.getValueAsInt(), 0);
		armorStand = location.getWorld().spawnEntity(nameTagLocation, EntityType.valueOf("ARMOR_STAND"));

		try {
			armorStand.getClass().getDeclaredMethod("setVisible", boolean.class).invoke(armorStand, false);
			armorStand.getClass().getMethod("setCustomNameVisible", boolean.class).invoke(armorStand, true);
			armorStand.getClass().getDeclaredMethod("setGravity", boolean.class).invoke(armorStand, false);
			nameTagName = type == SpawnType.NUMBERS ? ConfigMessages.WORLD_SPAWN_NUMBER.getValue().replace("%number%", String.valueOf(number)) : ConfigMessages.WORLD_SPAWN_PLAYER.getValue().replace("%number%", String.valueOf(number)).replace("%player%", player.getName());
			armorStand.getClass().getMethod("setCustomName", String.class).invoke(armorStand, nameTagName);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void delete() {
		location.getBlock().setType(Materials.GRASS_BLOCK.parseMaterial());
		location.add(0, 1, 0);
		location.clone().add(1, 0, 0).getBlock().setType(Material.AIR);
		location.clone().add(-1, 0, 0).getBlock().setType(Material.AIR);
		location.clone().add(0, 0, 1).getBlock().setType(Material.AIR);
		location.clone().add(0, 0, -1).getBlock().setType(Material.AIR);

		remove();
	}

	@Override
	public void onDeserializeEnd() {
		if(playerId != 0)
			this.player = VaroPlayer.getPlayer(playerId);

		if(nameTagLocation != null && nameTagName != null) {
			for(Entity ent : nameTagLocation.getWorld().getEntities()) {
				if(!ent.getType().toString().contains("ARMOR_STAND"))
					continue;

				try {
					String entName = ((String) ent.getClass().getMethod("getCustomName").invoke(ent));
					if(ent.getLocation().distance(nameTagLocation) < 1 && entName.equals(nameTagName)) {
						String nameTagName = type == SpawnType.NUMBERS ? ConfigMessages.WORLD_SPAWN_NUMBER.getValue().replace("%number%", String.valueOf(number)) : ConfigMessages.WORLD_SPAWN_PLAYER.getValue().replace("%number%", String.valueOf(number)).replace("%player%", player.getName());
						this.armorStand = ent;

						if(!nameTagName.equals(entName))
							armorStand.getClass().getMethod("setCustomName", String.class).invoke(armorStand, nameTagName);
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onSerializeStart() {
		this.playerId = player != null ? player.getId() : 0;
	}

	public Location getLocation() {
		return location;
	}

	public Location getNameTagLocation() {
		return nameTagLocation;
	}

	public int getNumber() {
		return number;
	}

	public VaroPlayer getPlayer() {
		return player;
	}

	public SpawnType getType() {
		return type;
	}

	public void setPlayer(VaroPlayer player) {
		this.player = player;
	}

	public static Spawn getSpawn(int number) {
		for(Spawn spawn : spawns) {
			if(spawn.getNumber() != number)
				continue;

			return spawn;
		}

		return null;
	}

	public static Spawn getSpawn(Location location) {
		for(Spawn spawn : spawns) {
			if(spawn.getLocation().distance(location) < 1)
				continue;

			return spawn;
		}

		return null;
	}

	public static Spawn getSpawn(VaroPlayer player) {
		for(Spawn spawn : spawns) {
			if(spawn.getPlayer() == null || !spawn.getPlayer().equals(player))
				continue;

			return spawn;
		}

		return null;
	}

	public static ArrayList<Spawn> getSpawns(SpawnType type) {
		ArrayList<Spawn> spawns = new ArrayList<>();
		for(Spawn spawn : spawns) {
			if(spawn.getType() != type)
				continue;

			spawns.add(spawn);
		}

		return null;
	}

	public static ArrayList<Spawn> getSpawnsClone() {
		ArrayList<Spawn> returnSpawns = new ArrayList<>();
		for(Spawn spawn : spawns) {
			returnSpawns.add(spawn);
		}
		return returnSpawns;
	}

	public static ArrayList<Spawn> getSpawns() {
		return spawns;
	}
}