package de.cuuky.varo.world;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.ServerPropertiesReader;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.world.border.VaroBorder;

public class WorldHandler {

	private World world;
	private VaroBorder border;

	public WorldHandler() {
		this.world = Bukkit.getWorld((String) new ServerPropertiesReader().get("level-name"));
		Bukkit.getServer().setSpawnRadius(ConfigEntry.SPAWN_PROTECTION_RADIUS.getValueAsInt());

		this.border = new VaroBorder(world);
		new TimeTimer();
	}

	public Location getTeleportLocation() {
		return Main.getGame().getLobby() != null ? Main.getGame().getLobby() : world.getSpawnLocation().add(0, 5, 0);
	}

	public World getWorld() {
		return world;
	}
	
	public VaroBorder getBorder() {
		return border;
	}
}
