package de.cuuky.varo;

import de.cuuky.cfw.configuration.serialization.Serialize;
import de.cuuky.cfw.serialization.CompatibleLocation;
import de.cuuky.cfw.version.BukkitVersion;
import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Varo;
import de.cuuky.varo.VaroElement;
import de.cuuky.varo.NameTag;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import org.bukkit.Location;
import org.bukkit.Material;

public class Spawn extends VaroElement {

    @Serialize("location")
    private CompatibleLocation location;

    @Serialize("nameTag")
    private NameTag nameTag;

    @Serialize("playerId")
    private int playerId;

    private VaroPlayer player;

    private Spawn(VaroPlayer player, int number, Location location) {
        this.id = number;
        this.location = new CompatibleLocation(location);
        this.playerId = player != null ? player.getId() : -1;
        this.player = player;
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

    private boolean nameTagEnabled() {
        return !VersionUtils.getVersion().isLowerThan(BukkitVersion.ONE_8)
            && ConfigSetting.NAMETAGS_OVER_SPAWN.getValueAsBoolean();
    }

    private void removeNameTag() {
        this.nameTag.remove();
        this.nameTag = null;
    }

    private String computeNameTagName() {
        return this.player == null ?
            ConfigMessages.SPAWNS_SPAWN_NUMBER.getValue().replace("%number%", String.valueOf(this.id)) :
            ConfigMessages.SPAWNS_SPAWN_PLAYER.getValue(null, this.player).replace("%number%", String.valueOf(this.id));
    }

    private void updateNameTag() {
        String name = this.computeNameTagName();
        if (this.nameTag != null) {
            this.nameTag.updateNameTag(name);
        } else this.nameTag = new NameTag(this.location.clone().add(0,
            ConfigSetting.NAMETAG_SPAWN_HEIGHT.getValueAsInt(), 0), name);
    }

    @Override
    protected void registerPolicies() {
        this.registerPolicy(VaroPlayer.class, this.player != null ? this.player::getId : () -> -1, null);
        super.registerPolicies();
    }

    @Override
    protected void onInitialize(Varo varo) {
        if (this.playerId != -1)
            this.player = this.varo.getPlayer(this.playerId).orElse(null);

        if (this.nameTagEnabled()) {
            this.updateNameTag();
        } else if (this.nameTag != null)
            this.removeNameTag();
    }

    @Override
    public void remove() {
        this.removeNameTag();
        super.remove();
    }

    public void resetEnvironment() {
        location.getBlock().setType(Materials.GRASS_BLOCK.parseMaterial());
        location.clone().add(1, 1, 0).getBlock().setType(Material.AIR);
        location.clone().add(-1, 1, 0).getBlock().setType(Material.AIR);
        location.clone().add(0, 1, 1).getBlock().setType(Material.AIR);
        location.clone().add(0, 1, -1).getBlock().setType(Material.AIR);

        this.remove();
    }

    public Location getLocation() {
        return this.location;
    }

    public VaroPlayer getPlayer() {
        return this.player;
    }

    public void setPlayer(VaroPlayer player) {
        this.player = player;
        if (this.nameTagEnabled()) {
            this.nameTag.updateNameTag(this.computeNameTagName());
        }
    }
}