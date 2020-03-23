package de.cuuky.varo.game.world;

import org.bukkit.World;

import de.cuuky.varo.game.world.border.VaroWorldBorder;
import de.cuuky.varo.version.BukkitVersion;
import de.cuuky.varo.version.VersionUtils;

public class VaroWorld {
	
	private World world;
	private VaroWorldBorder varoWorldBorder;
	
	public VaroWorld(World world) {
		this.world = world;
		
		if(VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7))
			this.varoWorldBorder = new VaroWorldBorder(world);
	}
	
	public World getWorld() {
		return this.world;
	}
	
	public VaroWorldBorder getVaroBorder() {
		return this.varoWorldBorder;
	}
}