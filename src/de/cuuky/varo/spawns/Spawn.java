package de.cuuky.varo.spawns;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import de.cuuky.cfw.version.BukkitVersion;
import de.cuuky.cfw.version.ServerSoftware;
import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;

public class Spawn implements VaroSerializeable {

    private static List<Spawn> spawns = new ArrayList<>();

    @VaroSerializeField(path = "location")
    private Location location;

    @Deprecated
    @VaroSerializeField(path = "nameTagLocation")
    private Location nameTagLocation;

    @Deprecated
    @VaroSerializeField(path = "nameTagName")
    private String nameTagName;
    
    @VaroSerializeField(path = "nameTagUuid")
    private String nametagUuid;

    @VaroSerializeField(path = "number")
    private int number;

    @VaroSerializeField(path = "playerId")
    private int playerId;

    private VaroPlayer player;

    public Spawn() {
        spawns.add(this);
    }

    private Spawn(VaroPlayer player, int number, Location location) {
        if (number == -1) {
            number = generateId();
        } else {
            Spawn spawn = getSpawn(number);
            if (spawn != null)
                spawn.remove();
        }

        this.number = number;
        this.location = location;
        this.player = player;
        
        if (shouldHaveNameTag())
        	this.createNameTag();
        
        spawns.add(this);
    }

    public Spawn(VaroPlayer player, Location location) {
        this(player, -1, location);
    }

    public Spawn(Location location) {
        this(null, location);
    }

    public Spawn(int number, Location location) {
        this(null, number, location);
    }

    private int generateId() {
        int i = spawns.size() + 1;
        while (getSpawn(i) != null)
            i++;

        return i;
    }
    
    private boolean shouldHaveNameTag() {
    	return !VersionUtils.getVersion().isLowerThan(BukkitVersion.ONE_8) && ConfigSetting.SET_NAMETAGS_OVER_SPAWN.getValueAsBoolean();
    }
    
    private Entity createNameTag() {
        Entity armorStand = this.location.getWorld().spawnEntity(
        		this.location.clone().add(0, ConfigSetting.NAMETAG_SPAWN_HEIGHT.getValueAsInt(), 0), EntityType.ARMOR_STAND);
        this.nametagUuid = armorStand.getUniqueId().toString();
        this.updateNameTag(armorStand);
		return armorStand;
    }
    
    private void updateNameTag(Entity armorStand) {
    	VersionUtils.getVersionAdapter().setArmorstandAttributes(armorStand,
                false, true, false, this.computeNameTagName());
    }
    
    private void updateNameTag() {
    	this.findNameTag().ifPresent(this::updateNameTag);
    }
    
    private void removeNameTag() {
    	this.findNameTag().ifPresent(Entity::remove);
    	this.nametagUuid = null;
    }

    private Optional<Entity> findNameTag() {
    	if (this.nametagUuid == null || VersionUtils.getVersion().isLowerThan(BukkitVersion.ONE_8))
    		return Optional.empty();
    	
    	String uuid = this.nametagUuid.toString();
    	return Arrays.stream(location.getChunk().getEntities()).
    			filter(entity -> EntityType.ARMOR_STAND.equals(entity.getType()) && uuid.equals(entity.getUniqueId().toString())).findAny();
    }

    private String computeNameTagName() {
        return this.player == null ?
            ConfigMessages.SPAWNS_SPAWN_NUMBER.getValue().replace("%number%", String.valueOf(number)) :
            ConfigMessages.SPAWNS_SPAWN_PLAYER.getValue(null, this.player).replace("%number%", String.valueOf(number));
    }

    private void remove() {
        this.removeNameTag();
        spawns.remove(this);
    }

    public void delete() {
        location.getBlock().setType(Materials.GRASS_BLOCK.parseMaterial());
        location.clone().add(1, 1, 0).getBlock().setType(Material.AIR);
        location.clone().add(-1, 1, 0).getBlock().setType(Material.AIR);
        location.clone().add(0, 1, 1).getBlock().setType(Material.AIR);
        location.clone().add(0, 1, -1).getBlock().setType(Material.AIR);

        remove();
    }

    @Override
    public void onDeserializeEnd() {
        if (this.location == null)
            remove();

        if (this.playerId != -1)
            this.player = VaroPlayer.getPlayer(playerId);
        
        // legacy support
        this.nameTagName = null;
        if (this.nameTagLocation != null) {
        	this.nameTagLocation.getWorld().getNearbyEntities(this.nameTagLocation, 1d, 1d, 1d).stream()
        		.filter(entity -> EntityType.ARMOR_STAND.equals(entity.getType())).findAny().ifPresent(Entity::remove);
        	this.nameTagLocation = null;
        }
        // legacy support end

        if (VersionUtils.getServerSoftware() == ServerSoftware.PAPER && VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_16))
        	// temporary paper 1.17+ workaround
        	try {
				Method forceLoadMethod = Chunk.class.getMethod("setForceLoaded", boolean.class);
				forceLoadMethod.invoke(this.location.getChunk(), true);
				
				this.location.getChunk().load();
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
					this.checkNameTag();
					try {
						forceLoadMethod.invoke(this.location.getChunk(), false);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}, 5L * 20L);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
        else {
	        this.checkNameTag();
        }
    }
    
    private void checkNameTag() {
    	if (this.shouldHaveNameTag()) {
        	Optional<Entity> armorStand = this.findNameTag();
        	if (armorStand.isPresent())
        		this.updateNameTag(armorStand.get());
        	else
        		this.createNameTag();
        } else
        	this.removeNameTag();
    }

    @Override
    public void onSerializeStart() {
        this.playerId = player != null ? player.getId() : -1;
    }

    public Location getLocation() {
        return location;
    }

    public int getNumber() {
        return number;
    }

    public VaroPlayer getPlayer() {
        return player;
    }

    public void setPlayer(VaroPlayer player) {
        this.player = player;
        this.updateNameTag();
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

    public static List<Spawn> getSpawnsClone() {
        return new ArrayList<>(spawns);
    }

    public static List<Spawn> getSpawns() {
        return spawns;
    }
}