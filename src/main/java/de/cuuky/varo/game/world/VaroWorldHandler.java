package de.cuuky.varo.game.world;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.game.world.border.decrease.BorderDecrease;
import de.cuuky.varo.game.world.border.decrease.DecreaseReason;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.varoplugin.cfw.version.ServerVersion;
import de.varoplugin.cfw.version.VersionUtils;

public class VaroWorldHandler {

    private final VaroWorld mainVaroWorld;
    private final ArrayList<VaroWorld> worlds = new ArrayList<>();
    private final Queue<BorderDecrease> borderDecreases = new LinkedList<>();
    private BorderDecrease activeBorderDecrease;

    public VaroWorldHandler() {
        World mainworld = Bukkit.getWorld(VersionUtils.getVersionAdapter().getServerProperties().getProperty("level-name"));
        fixWorldSpawn(mainworld);
        this.mainVaroWorld = new VaroWorld(mainworld);

        this.worlds.add(mainVaroWorld);

        for (World world : Bukkit.getWorlds())
            if (!world.equals(mainVaroWorld.getWorld()))
                addWorld(world);

        if (VersionUtils.getVersion() == ServerVersion.ONE_8)
            disableWorldDownloader();
    }

    private void disableWorldDownloader() {
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(Main.getInstance(), "WDL|INIT", (channel, player, data) -> {
            if (player.hasPermission("varo.worlddownloader"))
                return;

            Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, player.getName() + " nutzt einen WorldDownloader!", player.getUniqueId());
            Bukkit.broadcastMessage("§4" + player.getName() + " nutzt einen WorldDownloader!");
            player.kickPlayer("§4WorldDownloader sind bei Varos untersagt");
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

        if (!isBorderMinimumSize()) {
            this.borderDecreases.add(new BorderDecrease(reason.getSize(), reason.getDecreaseSpeed(), reason));
            runBorderCheck();
        }
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
        long time = (long) (diff / this.activeBorderDecrease.getSpeed());
        this.setBorderSize(newSize, time, null);
        
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            this.activeBorderDecrease.getReason().postAlert();
            this.activeBorderDecrease = null;

            if (this.isBorderMinimumSize()) {
                Main.getLanguageManager().broadcastMessage(ConfigMessages.BORDER_MINIMUM_REACHED);
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
        return Main.getVaroGame().getLobby() != null ? Main.getVaroGame().getLobby() : this.mainVaroWorld.getWorld().getSpawnLocation();
    }

    private static void fixWorldSpawn(World world) {
        Location location = world.getSpawnLocation();
        while (location.getBlock().getType().isSolid() || location.getBlock().isLiquid())
            location.add(0, 1, 0);
        world.setSpawnLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }
}