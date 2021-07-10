package de.cuuky.varo.spawns;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import de.cuuky.cfw.version.BukkitVersion;
import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;

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

	private Entity armorStand;
	private VaroPlayer player;

	public Spawn() {
		spawns.add(this);
	}

	public Spawn(Location location) {
		this.number = generateId();
		this.location = location;

		setNameTag();

		spawns.add(this);
	}

	public Spawn(VaroPlayer player, Location location) {
		this.number = generateId();
		this.location = location;
		this.player = player;

		setNameTag();

		spawns.add(this);
	}

	public Spawn(int number, Location location) {
		if (getSpawn(number) != null)
			getSpawn(number).remove();

		this.number = number;
		this.location = location;

		setNameTag();

		spawns.add(this);
	}

	private int generateId() {
		int i = spawns.size() + 1;
		while (getSpawn(i) != null)
			i++;

		return i;
	}

	private String getNametagName() {
		return this.player == null ? ConfigMessages.SPAWNS_SPAWN_NUMBER.getValue().replace("%number%", String.valueOf(number)) : ConfigMessages.SPAWNS_SPAWN_PLAYER.getValue(null, this.player).replace("%number%", String.valueOf(number));
	}

	private void remove() {
		removeNameTag();
		spawns.remove(this);
	}

	private void removeNameTag() {
		if (armorStand != null)
			armorStand.remove();
	}

	private void setNameTag() {
		if (!ConfigSetting.SET_NAMETAGS_OVER_SPAWN.getValueAsBoolean() || !VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7))
			return;

		nameTagLocation = location.clone().add(0, ConfigSetting.NAMETAG_SPAWN_HEIGHT.getValueAsInt(), 0);
		armorStand = location.getWorld().spawnEntity(nameTagLocation, EntityType.ARMOR_STAND);

		updateNametag();
	}

	public void updateNametag() {
		if(this.armorStand != null)
			VersionUtils.getVersionAdapter().setArmorstandAttributes(this.armorStand, false, true, false, this.getNametagName());
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
		if (this.location == null)
			remove();

		if (playerId != -1)
			this.player = VaroPlayer.getPlayer(playerId);

		if (nameTagLocation != null && nameTagName != null) {
			for (Entity ent : nameTagLocation.getWorld().getEntities()) {
				if (!ent.getType().toString().contains("ARMOR_STAND"))
					continue;

				try {
					String entName = ((String) ent.getClass().getMethod("getCustomName").invoke(ent));
					if (ent.getLocation().distance(nameTagLocation) < 1 && entName.equals(nameTagName)) {
						this.armorStand = ent;

						if (!ConfigSetting.SET_NAMETAGS_OVER_SPAWN.getValueAsBoolean() && armorStand != null) {
							this.armorStand.remove();
							this.armorStand = null;
						} else {
							updateNametag();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (this.armorStand == null)
				setNameTag();
		}
	}

	@Override
	public void onSerializeStart() {
		this.playerId = player != null ? player.getId() : -1;
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

	public void setPlayer(VaroPlayer player) {
		this.player = player;

		updateNametag();
	}

	public static Spawn getSpawn(int number) {
		for (Spawn spawn : spawns) {
			if (spawn.getNumber() != number)
				continue;

			return spawn;
		}

		return null;
	}

	public static Spawn getSpawn(VaroPlayer player) {
		for (Spawn spawn : spawns) {
			if (spawn.getPlayer() == null || !spawn.getPlayer().equals(player))
				continue;

			return spawn;
		}

		return null;
	}

	public static ArrayList<Spawn> getSpawnsClone() {
		return new ArrayList<>(spawns);
	}

	public static ArrayList<Spawn> getSpawns() {
		return spawns;
	}
}