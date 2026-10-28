package de.varoplugin.varo.game.world;

import de.varoplugin.cfw.scheduler.TaskScheduler;
import de.varoplugin.cfw.version.ServerVersion;
import de.varoplugin.cfw.version.VersionUtils;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.game.world.border.decrease.BorderDecrease;
import de.varoplugin.varo.game.world.border.decrease.DecreaseReason;
import de.varoplugin.varo.logger.logger.EventLogger.LogType;
import de.varoplugin.varo.utils.VaroUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.LinkedList;
import java.util.Queue;

public class VaroWorldHandler {

    private final VaroWorld mainVaroWorld;
    private final ArrayList<VaroWorld> worlds = new ArrayList<>();
    private final Queue<BorderDecrease> borderDecreases = new LinkedList<>();
    private volatile BorderDecrease activeBorderDecrease;
    private final Map<World, Location> cachedSpawns = new ConcurrentHashMap<>();

    public VaroWorldHandler() {
        World mainworld = Bukkit.getWorld(VersionUtils.getVersionAdapter().getServerProperties().getProperty("level-name"));
        this.initializeSpawn(mainworld);
        this.mainVaroWorld = new VaroWorld(mainworld);

        this.worlds.add(mainVaroWorld);

        for (World world : Bukkit.getWorlds())
            if (!world.equals(mainVaroWorld.getWorld()))
                addWorld(world);

        if (VersionUtils.getVersion() == ServerVersion.VERSION_1_8)
            disableWorldDownloader();
    }

    /**
     * On Folia, blocks can only be accessed from the thread owning the region. The world spawn is therefore fixed (and
     * cached) in a task running on the region of the spawn.
     */
    private void initializeSpawn(World world) {
        TaskScheduler scheduler = Main.scheduler();
        if (!scheduler.isRegionized()) {
            fixWorldSpawn(world);
            return;
        }

        scheduler.runAt(world.getSpawnLocation(), () -> {
            Location fixed = findFreeSpawn(world);
            // World settings can only be changed by the global region
            scheduler.run(() -> {
                world.setSpawnLocation(fixed.getBlockX(), fixed.getBlockY(), fixed.getBlockZ());
                this.refreshWorldSpawn(world);
            });
        });
    }

    private void disableWorldDownloader() {
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(Main.getInstance(), "WDL|INIT", (channel, player, data) -> {
            if (player.hasPermission("varo.worlddownloader"))
                return;

            Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, player.getName() + " nutzt einen WorldDownloader!", player.getUniqueId());
            Bukkit.broadcastMessage("§4" + player.getName() + " nutzt einen WorldDownloader!");
            VaroUtils.kickPlayer(player, "§4WorldDownloader sind bei Varos untersagt");
        });
    }

    public void addWorld(World world) {
        VaroWorld vworld = new VaroWorld(world);
        this.worlds.add(vworld);

        if (ConfigSetting.WORLD_SNCHRONIZE_BORDER.getValueAsBoolean())
            vworld.getVaroBorder().setSize(getBorderSize(), 0);
    }

    public void decreaseBorder(DecreaseReason reason) {
        if (!reason.isEnabled())
            return;

        // Can be called by any thread (e.g. a player's region thread on Folia). The queue is only accessed by the main/global thread
        Main.scheduler().execute(() -> {
            if (!isBorderMinimumSize()) {
                this.borderDecreases.add(new BorderDecrease(reason.getSize(), reason.getDecreaseSpeed(), reason));
                runBorderCheck();
            }
        });
    }
    
    private boolean isBorderMinimumSize() {
        return this.getBorderSize() <= ConfigSetting.MIN_BORDER_SIZE.getValueAsInt();
    }
    
    private void runBorderCheck() {
        if (this.activeBorderDecrease != null || this.borderDecreases.isEmpty())
            return;
        this.activeBorderDecrease = this.borderDecreases.poll();
        this.activeBorderDecrease.getReason().postBroadcast();
        
        double size = this.getBorderSize();
        double newSize = Math.max(size - this.activeBorderDecrease.getAmount(), ConfigSetting.MIN_BORDER_SIZE.getValueAsInt());
        double diff = Math.abs(size - newSize);
        long time = BigDecimal.valueOf(diff).divide(this.activeBorderDecrease.getSpeed(), RoundingMode.HALF_UP).longValue();
        this.setBorderSize(newSize, time, null);
        
        Main.scheduler().runLater(() -> {
            this.activeBorderDecrease.getReason().postLog();
            this.activeBorderDecrease = null;

            if (this.isBorderMinimumSize()) {
                Messages.BORDER_MINIMUM_REACHED.broadcast();
                this.borderDecreases.clear();
            } else
                this.runBorderCheck();
        }, time * 20L);
    }
    
    public double getBorderSize(World world) {
        if (ConfigSetting.WORLD_SNCHRONIZE_BORDER.getValueAsBoolean())
            return getBorderSize();

        VaroWorld vworld = world != null ? getVaroWorld(world) : this.mainVaroWorld;
        return vworld.getVaroBorder().getSize();
    }

    public double getBorderSize() {
        return this.mainVaroWorld.getVaroBorder().getSize();
    }

    public void setBorderSize(double size, long time, World world) {
        if (ConfigSetting.WORLD_SNCHRONIZE_BORDER.getValueAsBoolean())
            for (VaroWorld vworld : worlds)
                vworld.getVaroBorder().setSize(size, time);
        else {
            VaroWorld vworld = world != null ? getVaroWorld(world) : mainVaroWorld;
            vworld.getVaroBorder().setSize(size, time);
        }
    }

    public double getBorderRadius(World world) {
        if (ConfigSetting.WORLD_SNCHRONIZE_BORDER.getValueAsBoolean())
            return getBorderRadius();

        VaroWorld vworld = world != null ? getVaroWorld(world) : this.mainVaroWorld;
        return vworld.getVaroBorder().getRadius();
    }

    public double getBorderRadius() {
        return this.mainVaroWorld.getVaroBorder().getRadius();
    }

    public VaroWorld getVaroWorld(World world) {
        for (VaroWorld vworld : worlds)
            if (vworld.getWorld().equals(world))
                return vworld;

        throw new IllegalArgumentException("Couldn't find VaroWorld " + world.getName());
    }

    public ArrayList<VaroWorld> getWorlds() {
        return this.worlds;
    }

    public VaroWorld getMainWorld() {
        return this.mainVaroWorld;
    }

    public Location getTeleportLocation() {
        return Main.getVaroGame().getLobby() != null ? Main.getVaroGame().getLobby() : this.getWorldSpawn(this.mainVaroWorld.getWorld());
    }

    public Location getWorldSpawn(World world) {
        if (Main.scheduler().isRegionized()) {
            Location cached = this.cachedSpawns.get(world);
            return cached != null ? cached.clone() : world.getSpawnLocation();
        }

        return this.calculateWorldSpawn(world);
    }

    /**
     * Recalculates the cached world spawn after it has been changed. Only required on Folia.
     */
    public void refreshWorldSpawn(World world) {
        TaskScheduler scheduler = Main.scheduler();
        if (scheduler.isRegionized())
            scheduler.runAt(world.getSpawnLocation(), () -> this.cachedSpawns.put(world, this.calculateWorldSpawn(world)));
    }

    private Location calculateWorldSpawn(World world) {
        Location spawn = world.getSpawnLocation();
        if (!VaroUtils.isNotSolidTerrainOrLiquid(spawn.getBlock()) || !VaroUtils.isNotSolidTerrainOrLiquid(spawn.clone().add(0, 1, 0).getBlock())) {
            Main.getInstance().getLogger().warning("World spawn of world " + world.getName() + " is obstructed! Players will be teleported to a different location!");

            spawn = world.getHighestBlockAt(spawn).getLocation().add(0, 1, 0);
        }
        return spawn;
    }

    public Location getMainWorldSpawn() {
        return this.getWorldSpawn(this.getMainWorld().getWorld());
    }

    private static Location findFreeSpawn(World world) {
        Location location = world.getSpawnLocation();
        while (location.getBlock().getType().isSolid() || location.getBlock().isLiquid())
            location.add(0, 1, 0);
        return location;
    }

    private static void fixWorldSpawn(World world) {
        Location location = findFreeSpawn(world);
        Main.scheduler().execute(() -> world.setSpawnLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }
}