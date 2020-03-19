package de.cuuky.varo.game.world;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import de.cuuky.varo.Main;
import de.cuuky.varo.game.world.border.VaroBorder;
import de.cuuky.varo.logger.logger.EventLogger.LogType;

public class VaroWorld {

	private VaroBorder varoBorder;
	private World world;

	public VaroWorld() {
		this.varoBorder = new VaroBorder();
		this.world = Bukkit.getWorld(Main.getDataManager().getPropertiesReader().getConfiguration().get("level-name"));
		
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

	public VaroBorder getVaroBorder() {
		return this.varoBorder;
	}

	public World getWorld() {
		return this.world;
	}

	public Location getTeleportLocation() {
		return Main.getVaroGame().getLobby() != null ? Main.getVaroGame().getLobby() : world.getSpawnLocation().add(0, 5, 0);
	}

	// TODO: Multiworld with own Borders

}