package de.cuuky.varo.game.world;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import de.cuuky.varo.Main;
import de.cuuky.varo.game.world.border.decrease.BorderDecrease;
import de.cuuky.varo.game.world.border.decrease.DecreaseReason;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.version.BukkitVersion;
import de.cuuky.varo.version.VersionUtils;

public class VaroWorldHandler {

	private VaroWorld mainVaroWorld;
	private ArrayList<VaroWorld> worlds;

	private double borderSize;

	public VaroWorldHandler() {
		World mainworld = Bukkit.getWorld(Main.getDataManager().getPropertiesReader().getConfiguration().get("level-name"));
		this.mainVaroWorld = new VaroWorld(mainworld);

		this.worlds = new ArrayList<>();
		this.worlds.add(mainVaroWorld);

		if(VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7))
			this.borderSize = this.mainVaroWorld.getVaroBorder().getBorderSize();

		for(World world : Bukkit.getWorlds())
			if(!world.equals(mainVaroWorld.getWorld()))
				addWorld(world);

		if(VersionUtils.getVersion() == BukkitVersion.ONE_8)
			disableWorldDownloader();
	}

	private void disableWorldDownloader() {
		Bukkit.getServer().getMessenger().registerIncomingPluginChannel(Main.getInstance(), "WDL|INIT", new PluginMessageListener() {

			@Override
			public void onPluginMessageReceived(String channel, Player player, byte[] data) {
				if(player.hasPermission("varo.worlddownloader"))
					return;

				Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, player.getName() + " nutzt einen WorldDownloader!");
				Bukkit.broadcastMessage("§4" + player.getName() + " nutzt einen WorldDownloader!");
				player.kickPlayer("§4WorldDownloader sind bei Varos untersagt");
			}
		});
	}

	public void addWorld(World world) {
		VaroWorld vworld = new VaroWorld(world);
		this.worlds.add(vworld);
		
		if(VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7)) 
			vworld.getVaroBorder().setBorderSize(this.borderSize, 0);
	}

	public void decreaseBorder(DecreaseReason reason) {
		if(!VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7) || !reason.isEnabled())
			return;

		BorderDecrease decr = new BorderDecrease(reason.getSize(), reason.getDecreaseSpeed());
		decr.setStartHook(new Runnable() {

			@Override
			public void run() {
				borderSize = borderSize - reason.getSize();
				reason.postBroadcast();
			}
		});

		decr.setFinishHook(new Runnable() {

			@Override
			public void run() {
				reason.postAlert();
			}
		});
	}

	public void setBorderSize(double size, long time) {
		if(!VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7))
			return;

		this.borderSize = size;
		for(VaroWorld world : worlds)
			world.getVaroBorder().setBorderSize(size, time);
	}
	
	public VaroWorld getVaroWorld(World world) {
		for(VaroWorld vworld : worlds) 
			if(vworld.getWorld().equals(world))
				return vworld;
		
		throw new NullPointerException("Couldn't find VaroWorld for " + world.getName());
	}

	public double getBorderSize() {
		return this.borderSize;
	}

	public ArrayList<VaroWorld> getWorlds() {
		return this.worlds;
	}

	public VaroWorld getMainWorld() {
		return this.mainVaroWorld;
	}

	public Location getTeleportLocation() {
		return Main.getVaroGame().getLobby() != null ? Main.getVaroGame().getLobby() : this.mainVaroWorld.getWorld().getSpawnLocation().add(0, 5, 0);
	}
}