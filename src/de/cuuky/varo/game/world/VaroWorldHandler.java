package de.cuuky.varo.game.world;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import de.varoplugin.cfw.version.ServerVersion;
import de.varoplugin.cfw.version.VersionUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.game.world.border.decrease.BorderDecrease;
import de.cuuky.varo.game.world.border.decrease.DecreaseReason;
import de.cuuky.varo.logger.logger.EventLogger.LogType;

public class VaroWorldHandler {

    private VaroWorld mainVaroWorld;
    private ArrayList<VaroWorld> worlds;

    public VaroWorldHandler() {
        World mainworld = Bukkit.getWorld(Main.getDataManager().getPropertiesReader().getConfiguration().get("level-name"));
        this.mainVaroWorld = new VaroWorld(mainworld);

        this.worlds = new ArrayList<>();
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

            Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, player.getName() + " nutzt einen WorldDownloader!");
            Bukkit.broadcastMessage("§4" + player.getName() + " nutzt einen WorldDownloader!");
            player.kickPlayer("§4WorldDownloader sind bei Varos untersagt");
        });
    }

    public void addWorld(World world) {
        VaroWorld vworld = new VaroWorld(world);
        this.worlds.add(vworld);

        if (VersionUtils.getVersion().isHigherThan(ServerVersion.ONE_7) && ConfigSetting.WORLD_SNCHRONIZE_BORDER.getValueAsBoolean())
            vworld.getVaroBorder().setBorderSize(getBorderSize(), 0);
    }

    public void decreaseBorder(DecreaseReason reason) {
        if (!VersionUtils.getVersion().isHigherThan(ServerVersion.ONE_7) || !reason.isEnabled())
            return;

        BorderDecrease decr = new BorderDecrease(reason.getSize(), reason.getDecreaseSpeed());
        decr.setStartHook(() -> reason.postBroadcast());
        decr.setFinishHook(() -> reason.postAlert());
    }

    public void setBorderSize(double size, long time, World world) {
        if (!VersionUtils.getVersion().isHigherThan(ServerVersion.ONE_7))
            return;

        if (ConfigSetting.WORLD_SNCHRONIZE_BORDER.getValueAsBoolean())
            for (VaroWorld vworld : worlds)
                vworld.getVaroBorder().setBorderSize(size, time);
        else {
            VaroWorld vworld = world != null ? getVaroWorld(world) : mainVaroWorld;
            vworld.getVaroBorder().setBorderSize(size, time);
        }
    }

    public VaroWorld getVaroWorld(World world) {
        for (VaroWorld vworld : worlds)
            if (vworld.getWorld().equals(world))
                return vworld;

        throw new NullPointerException("Couldn't find VaroWorld for " + world.getName());
    }

    public double getBorderSize(World world) {
        if (ConfigSetting.WORLD_SNCHRONIZE_BORDER.getValueAsBoolean())
            return getBorderSize();
        else {
            if (!VersionUtils.getVersion().isHigherThan(ServerVersion.ONE_7))
                return 0;

            VaroWorld vworld = world != null ? getVaroWorld(world) : this.mainVaroWorld;
            return vworld.getVaroBorder().getBorderSize();
        }
    }

    public double getBorderSize() {
        if (!VersionUtils.getVersion().isHigherThan(ServerVersion.ONE_7))
            return 0;

        return this.mainVaroWorld.getVaroBorder().getBorderSize();
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
}