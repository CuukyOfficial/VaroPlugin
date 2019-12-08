package de.cuuky.varo.world;

import de.cuuky.varo.game.Game;
import de.cuuky.varo.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.ServerPropertiesReader;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.world.border.VaroBorder;

public class WorldHandler {

	private static WorldHandler instance;

	private World world;
	private VaroBorder border;

	public static WorldHandler getInstance() {
		if (instance == null) {
			instance = new WorldHandler();
		}
		return instance;
	}

	private WorldHandler() {
		this.world = Bukkit.getWorld((String) new ServerPropertiesReader().get("level-name"));
		Bukkit.getServer().setSpawnRadius(ConfigEntry.SPAWN_PROTECTION_RADIUS.getValueAsInt());

		this.border = new VaroBorder(world);
		Utils.setWorldToTime();
	}

	public Location getTeleportLocation() {
		return Game.getInstance().getLobby() != null ? Game.getInstance().getLobby() : world.getSpawnLocation().add(0, 5, 0);
	}

	public World getWorld() {
		return world;
	}

	public VaroBorder getBorder() {
		return border;
	}
}
