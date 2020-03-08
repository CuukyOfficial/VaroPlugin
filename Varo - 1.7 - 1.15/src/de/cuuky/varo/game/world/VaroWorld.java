package de.cuuky.varo.game.world;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import de.cuuky.varo.Main;
import de.cuuky.varo.game.world.border.VaroBorder;

public class VaroWorld {
	
	private VaroBorder varoBorder;
	private World world;
	
	public VaroWorld() {
		this.varoBorder = new VaroBorder();
		this.world = Bukkit.getWorld(Main.getDataManager().getPropertiesReader().getConfiguration().get("level-name"));
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