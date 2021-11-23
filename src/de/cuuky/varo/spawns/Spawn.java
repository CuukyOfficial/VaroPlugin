package de.cuuky.varo.spawns;

import de.cuuky.cfw.version.BukkitVersion;
import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class Spawn implements VaroSerializeable {

    private static List<Spawn> spawns = new ArrayList<>();

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

    private Spawn(VaroPlayer player, int number, Location location) {
        System.out.println(number + "," + location);
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
        this.setNameTag();
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

    private void findNameTag() {
        if (this.nameTagLocation == null || this.nameTagName == null)
            return;

        for (Entity ent : nameTagLocation.getWorld().getNearbyEntities(nameTagLocation, 1d, 1d, 1d)) {
            if (!ent.getType().toString().contains("ARMOR_STAND"))
                continue;

            try {
                String entName = ((String) ent.getClass().getMethod("getCustomName").invoke(ent));
                if (!entName.equals(this.nameTagName))
                    continue;

                this.armorStand = ent;
                String newName = this.getNametagName();
                if (!ConfigSetting.SET_NAMETAGS_OVER_SPAWN.getValueAsBoolean()) {
                    this.removeNameTag();
                } else if (!newName.equals(this.nameTagName)) {
                    this.updateNametag(this.getNametagName());
                }
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getNametagName() {
        return this.player == null ?
            ConfigMessages.SPAWNS_SPAWN_NUMBER.getValue().replace("%number%", String.valueOf(number)) :
            ConfigMessages.SPAWNS_SPAWN_PLAYER.getValue(null, this.player).replace("%number%", String.valueOf(number));
    }

    private void remove() {
        this.removeNameTag();
        spawns.remove(this);
    }

    private void removeNameTag() {
        if (this.armorStand == null)
            return;

        this.armorStand.remove();
        this.armorStand = null;
    }

    private void setNameTag() {
        if (!ConfigSetting.SET_NAMETAGS_OVER_SPAWN.getValueAsBoolean() ||
            !VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7))
            return;

        nameTagLocation = location.clone().add(0, ConfigSetting.NAMETAG_SPAWN_HEIGHT.getValueAsInt(), 0);
        armorStand = location.getWorld().spawnEntity(nameTagLocation, EntityType.ARMOR_STAND);
        this.updateNametag(this.getNametagName());
    }

    public void updateNametag(String name) {
        if (this.armorStand == null)
            return;

        VersionUtils.getVersionAdapter().setArmorstandAttributes(this.armorStand,
            false, true, false, this.nameTagName = name);
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

        this.findNameTag();
        if (this.armorStand == null)
            this.setNameTag();
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
        this.updateNametag(this.getNametagName());
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